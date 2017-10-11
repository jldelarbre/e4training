package com.sii.rental.ui.views;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;

public class RentalTreeViewPart {

	@PostConstruct
	public void createContent(Composite parent, RentalAgency ra, IEclipseContext ctx) {
		TreeViewer tv = new TreeViewer(parent);
		
		RentalGuiElementProvider rentalTreeProvider = ContextInjectionFactory.make(RentalGuiElementProvider.class, ctx);
		tv.setContentProvider(rentalTreeProvider);
		tv.setLabelProvider(rentalTreeProvider);
		
		Collection<RentalAgency> agencies = new HashSet<>();
		agencies.add(ra);
		tv.setInput(agencies);
		
		tv.expandAll();
	}
	
}
