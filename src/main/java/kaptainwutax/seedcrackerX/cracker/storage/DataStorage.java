package kaptainwutax.seedcrackerX.cracker.storage;

import com.seedfinding.mcfeature.Feature;
import com.seedfinding.mcfeature.decorator.DesertWell;
import com.seedfinding.mcfeature.decorator.EndGateway;
import com.seedfinding.mcfeature.structure.BuriedTreasure;
import com.seedfinding.mcfeature.structure.Structure;
import com.seedfinding.mcfeature.structure.TriangularStructure;
import com.seedfinding.mcfeature.structure.UniformStructure;
import io.netty.util.internal.ConcurrentSet;
import kaptainwutax.seedcrackerX.cracker.BiomeData;
import kaptainwutax.seedcrackerX.cracker.DataAddedEvent;
import kaptainwutax.seedcrackerX.cracker.HashedSeedData;
import kaptainwutax.seedcrackerX.cracker.PillarData;
import kaptainwutax.seedcrackerX.cracker.decorator.Dungeon;
import kaptainwutax.seedcrackerX.cracker.decorator.EmeraldOre;
import kaptainwutax.seedcrackerX.cracker.decorator.WarpedFungus;
import kaptainwutax.seedcrackerX.finder.decorator.DungeonFinder;
import kaptainwutax.seedcrackerX.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class DataStorage {

	public static final Comparator<Entry<Feature.Data<?>>> SEED_DATA_COMPARATOR = (s1, s2) -> {
		boolean isStructure1 = s1.data.feature instanceof Structure;
		boolean isStructure2 = s2.data.feature instanceof Structure;

		//Structures always come before decorators.
		if(isStructure1 != isStructure2) {
			return isStructure2 ? 1: -1;
		}

		if(s1.equals(s2)) {
			return 0;
		}

		double diff = getBits(s2.data.feature) - getBits(s1.data.feature);
		return diff == 0 ? 1 : (int)Math.signum(diff);
	};

	protected TimeMachine timeMachine = new TimeMachine(this);
	protected Set<Consumer<DataStorage>> scheduledData = new ConcurrentSet<>();

	protected PillarData pillarData = null;
	protected ScheduledSet<Entry<Feature.Data<?>>> baseSeedData = new ScheduledSet<>(SEED_DATA_COMPARATOR);
	protected ScheduledSet<Entry<BiomeData>> biomeSeedData = new ScheduledSet<>(null);
	public HashedSeedData hashedSeedData = null;
	public List<Long> dungeon12StructureSeeds = new ArrayList<>();

	public void tick() {
		if(!this.timeMachine.isRunning) {
			this.baseSeedData.dump();
			this.biomeSeedData.dump();

			this.timeMachine.isRunning = true;

			TimeMachine.SERVICE.submit(() -> {
				try {
					this.scheduledData.removeIf(c -> {
						c.accept(this);
						return true;
					});
				} catch(Exception e) {
					e.printStackTrace();
				}

				this.timeMachine.isRunning = false;
			});
		}
	}

	public synchronized boolean addPillarData(PillarData data, DataAddedEvent event) {
		boolean isAdded = this.pillarData == null;

		if(isAdded && data != null) {
			this.pillarData = data;
			this.schedule(event::onDataAdded);
		}

		return isAdded;
	}

	public synchronized boolean addBaseData(Feature.Data<?> data, DataAddedEvent event) {
		Entry<Feature.Data<?>> e = new Entry<>(data, event);

		if(this.baseSeedData.contains(e)) {
			return false;
		}

		this.baseSeedData.scheduleAdd(e);
		this.schedule(event::onDataAdded);
		return true;
	}

	public synchronized boolean addBiomeData(BiomeData data, DataAddedEvent event) {
		Entry<BiomeData> e = new Entry<>(data, event);

		if(this.biomeSeedData.contains(e)) {
			return false;
		}

		this.biomeSeedData.scheduleAdd(e);
		this.schedule(event::onDataAdded);
		return true;
	}
	public boolean addDungeon12StructureSeed(long structureSeed) {
		if(this.dungeon12StructureSeeds.contains(structureSeed)) {
			//found matching structureseed
			Log.printSeed("found two matching structureseeds ${SEED}.", structureSeed);
			getTimeMachine().structureSeeds.add(structureSeed);
			getTimeMachine().poke(TimeMachine.Phase.STRUCTURES);
			getTimeMachine().poke(TimeMachine.Phase.BIOMES);
			return true;
		}
		this.dungeon12StructureSeeds.add(structureSeed);
		return false;
	}

	public synchronized boolean addHashedSeedData(HashedSeedData data, DataAddedEvent event) {
		if(this.hashedSeedData == null || this.hashedSeedData.getHashedSeed() != data.getHashedSeed()) {
			this.hashedSeedData = data;
			this.schedule(event::onDataAdded);
			return true;
		}

		return false;
	}

	public void schedule(Consumer<DataStorage> consumer) {
		this.scheduledData.add(consumer);
	}

	public TimeMachine getTimeMachine() {
		return this.timeMachine;
	}

	public double getBaseBits() {
		double bits = 0.0D;

		for(Entry<Feature.Data<?>> e: this.baseSeedData) {
			bits += getBits(e.data.feature);
		}

		return bits;
	}

	public double getWantedBits() {
		return 256D;
	}

	public static double getBits(Feature<?, ?> feature) {
		if(feature instanceof UniformStructure) {
			UniformStructure<?> s = (UniformStructure<?>)feature;
			return Math.log(s.getOffset() * s.getOffset()) / Math.log(2);
		} else if(feature instanceof TriangularStructure) {
			TriangularStructure<?> s = (TriangularStructure<?>)feature;
			return Math.log(s.getPeak() * s.getPeak()) / Math.log(2);
		}

		if(feature instanceof BuriedTreasure)return Math.log(100) / Math.log(2);
		if(feature instanceof DesertWell)return Math.log(1000 * 16 * 16) / Math.log(2);
		if(feature instanceof Dungeon)return Math.log(256 * 16 * 16 * 0.125D) / Math.log(2);
		if(feature instanceof EmeraldOre)return Math.log(28 * 16 * 16 * 0.5D) / Math.log(2);
		if(feature instanceof EndGateway)return Math.log(700 * 16 * 16 * 7) / Math.log(2);
		if(feature instanceof WarpedFungus)return 0;

		throw new UnsupportedOperationException("go do implement bits count for " + feature.getName() + " you fool");
	}

	public void clear() {
		this.scheduledData = new ConcurrentSet<>();
		this.pillarData = null;
		this.dungeon12StructureSeeds = new ArrayList<>();
		this.baseSeedData = new ScheduledSet<>(SEED_DATA_COMPARATOR);
		this.biomeSeedData = new ScheduledSet<>(null);
		//this.hashedSeedData = null;
		this.timeMachine.shouldTerminate = true;
		this.timeMachine = new TimeMachine(this);
		DungeonFinder.resetXRayDetected();
	}

	public static class Entry<T> {
		public final T data;
		public final DataAddedEvent event;

		public Entry(T data, DataAddedEvent event) {
			this.data = data;
			this.event = event;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o)return true;
			if(!(o instanceof Entry))return false;
			Entry<?> entry = (Entry<?>)o;

			if(this.data instanceof Feature.Data<?> && entry.data instanceof Feature.Data<?>) {
				Feature.Data<?> d1 = (Feature.Data<?>)this.data;
				Feature.Data<?> d2 = (Feature.Data<?>)entry.data;
				return d1.feature == d2.feature && d1.chunkX == d2.chunkX && d1.chunkZ == d2.chunkZ;
			} else if(this.data instanceof BiomeData && entry.data instanceof BiomeData) {
				return this.data.equals(entry.data);
			}

			return false;
		}

		@Override
		public int hashCode() {
			if(this.data instanceof Feature.Data<?>) {
				return ((Feature.Data<?>)this.data).chunkX * 31 + ((Feature.Data<?>)this.data).chunkZ;
			} else if(this.data instanceof BiomeData) {
				return this.data.hashCode();
			}

			return super.hashCode();
		}
	}

}
