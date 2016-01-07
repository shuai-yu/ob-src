package com.omnibounce.stages;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.omnibounce.drawobject.KActor;

public class StageManager<T extends KActor> extends BasicStage {
	
	// This list must be ordered.
	protected List<T> actorList = new ArrayList<T>();
	
	public final int getItemCount() {
		return actorList.size();
	}

	@Deprecated
	public final List<T> getAll() {
		return actorList;
	}
	
	public final List<T> enumAll() {
		return actorList;
	}

	public final ListIterator<T> getListIterator() {
		return actorList.listIterator();
	}

	public final T get(int id) {
		return actorList.get(id);
	}

	public final void set(int id, T obj) {
		actorList.set(id, obj);
	}

	public final void add(T obj) {
		actorList.add(obj);
		// add this actor also
		if (obj != null) obj.addToStage(this);
	}

	public final void add(T[] arr) {
		for (int i = 0; i < arr.length; i++)
			this.add(arr[i]);
	}

	public final void remove(int id) {
		KActor actor = actorList.get(id);
		if (actor != null) {
			// remove it from stage.
			actor.removeFromStage();
		}
		
		actorList.remove(id);
	}

	public final void remove(T obj) {
		assert(obj != null);
		if (obj != null) {
			obj.removeFromStage();
		}
		
		actorList.remove(obj);
	}
	
	public final void removeAll() {
		for (int i = 0; i < actorList.size(); i++) {
			this.remove(0);
		}
		assert( actorList.size() == 0);
	}

	// WARNING: destroy() doesn't remove the item nor set it to null!
	public final void destroy(int id) {
		destroy(actorList.get(id));
	}

	@Deprecated
	public final void destroy(T child) {
		if (child != null) {
			// destroy this actor also
			child.dispose();
		}
	}

	@Deprecated
	public final void destroyAll() {
		for (T child : actorList) {
			destroy(child);
		}
	}

	public final void clean() {
		destroyAll();
		removeAll();
	}
	
}
