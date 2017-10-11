package com.sii.rental.ui.views;


import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;
import com.sii.rental.ui.RentalUIConstants;

public class RentalGuiElementProvider extends LabelProvider implements ITreeContentProvider, IColorProvider, RentalUIConstants {
	
	@Inject @Named(RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry registry;

	@Override
	public Color getForeground(Object element) {
		if (element instanceof Customer) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA);
		}
		if (element instanceof Rental) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_CYAN);
		}
		if (element instanceof RentalObject) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RentalAgency) {
			RentalAgency ra = (RentalAgency) parentElement;
			return new Node[] {new Node(Node.CUSTOMERS, ra), new Node(Node.RENTAL_OBJECT, ra), new Node(Node.RENTAL, ra)};
		}
		if (parentElement instanceof Node) {
			return ((Node) parentElement).getChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Customer) {
			return registry.get(IMG_CUSTOMER);
		}
		if (element instanceof RentalObject) {
			return registry.get(IMG_RENTAL_OBJECT);
		}
		if (element instanceof Rental) {
			return registry.get(IMG_RENTAL);
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		}
		if (element instanceof Customer) {
			return ((Customer) element).getFirstName() + " " + ((Customer) element).getLastName();
		}
		if (element instanceof RentalObject) {
			return ((RentalObject) element).getName();
		}
		if (element instanceof Rental) {
			return element.toString();
		}
		return super.getText(element);
	}

	class Node {
		private static final String CUSTOMERS = "Customers";
		private static final String RENTAL_OBJECT = "Rental Objects";
		private static final String RENTAL = "Rents";
		private String label;
		private RentalAgency ra;
		public Node(String label, RentalAgency ra) {
			super();
			this.label = label;
			this.ra = ra;
		}
		
		public Object[] getChildren() {
			if (label == CUSTOMERS) {
				return ra.getCustomers().toArray();
			}
			if (label == RENTAL_OBJECT) {
				return ra.getObjectsToRent().toArray();
			}
			if (label == RENTAL) {
				return ra.getRentals().toArray();
			}
			return null;
		}
		
		@Override
		public String toString() {
			return label;
		}
	}
}
