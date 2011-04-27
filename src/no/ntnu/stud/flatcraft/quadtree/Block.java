package no.ntnu.stud.flatcraft.quadtree;

public enum Block {
	METAL, ROCK, RUBBER, WATER, ACID, EMPTY, GOAL, START;

	public Block next() {
		int index = (this.ordinal() + 1) % Block.values().length;
		return Block.values()[index];
	}

	public Block previous() {
		int index = (this.ordinal() + Block.values().length - 1)
				% Block.values().length;
		return Block.values()[index];
	}
}
