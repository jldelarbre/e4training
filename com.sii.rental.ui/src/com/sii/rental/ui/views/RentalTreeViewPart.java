package com.sii.rental.ui.views;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;

public class RentalTreeViewPart {

	@PostConstruct
	public void createContent(Composite parent, RentalAgency ra) {
		TreeViewer tv = new TreeViewer(parent);
		
		RentalGuiElementProvider rentalTreeProvider = new RentalGuiElementProvider();
		tv.setContentProvider(rentalTreeProvider);
		tv.setLabelProvider(rentalTreeProvider);
		
		Collection<RentalAgency> agencies = new HashSet<>();
		agencies.add(ra);
		tv.setInput(agencies);
		
		tv.expandAll();
	}
	
}
