package com.sii.rental.ui.views;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ui.RentalUIConstants;

public class RentalTreeViewPart implements RentalUIConstants {

	private TreeViewer tv;

	@PostConstruct
	public void createContent(Composite parent, RentalAgency ra, IEclipseContext ctx, ESelectionService ess, EMenuService menuService) {
		tv = new TreeViewer(parent);
		
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
		
		menuService.registerContextMenu(tv.getControl(), "com.sii.rental.eap.popupmenu.hello");//Pour menu popup et view dans treeview
	}
	
	@Inject
	void refreshColor(@Preference(value= PREF_PALETTE) String pal) {
		if (tv != null && !tv.getControl().isDisposed()) {
			tv.refresh();
		}
	}
}
