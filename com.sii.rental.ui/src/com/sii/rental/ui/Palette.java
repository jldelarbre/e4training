package com.sii.rental.ui;

import org.eclipse.jface.viewers.IColorProvider;

public class Palette {

	private String id, name;
	private IColorProvider provider;
	
	public Palette(String id, String name, IColorProvider provider) {
		this.id = id;
		this.name = name;
		this.provider = provider;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public IColorProvider getProvider() {
		return provider;
	}
}
