package com.sii.rental.ui.views;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;

public class RentalTreeViewPart {

	@PostConstruct
	public void createContent(Composite parent, RentalAgency ra, IEclipseContext ctx, ESelectionService ess) {
		TreeViewer tv = new TreeViewer(parent);
		
		RentalGuiElementProvider rentalTreeProvider = ContextInjectionFactory.make(RentalGuiElementProvider.class, ctx);
		tv.setContentProvider(rentalTreeProvider);
		tv.setLabelProvider(rentalTreeProvider);
		
		Collection<RentalAgency> agencies = new HashSet<>();
		agencies.add(ra);
		tv.setInput(agencies);
		
		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = event.getStructuredSelection();
				ess.setSelection((sel.size() == 1) ? sel.getFirstElement() : sel.toArray());
			}
		});
		
		tv.expandAll();
	}
	
}
