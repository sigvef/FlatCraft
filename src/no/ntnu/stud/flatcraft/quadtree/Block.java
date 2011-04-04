package no.ntnu.stud.flatcraft.quadtree;

public enum Block {
	METAL,ROCK,EARTH,RUBBER,WATER,ACID,EMPTY;

	public Block next() {
		int index = (this.ordinal() + 1) % Block.values().length;  
        return Block.values()[index];
	}
	
	public Block previous() {
		int index = (this.ordinal() - 1) % Block.values().length;  
        return Block.values()[index];
	}
}
