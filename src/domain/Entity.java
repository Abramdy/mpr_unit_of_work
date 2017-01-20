package domain;

public abstract class Entity {

	private EntityState state = EntityState.New;
	private int id;
	
	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
