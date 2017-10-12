package com.sii.rental.ui.views;


import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
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
	
	@Inject @Named(RENTAL_UI_PREF_STORE)
	private IPreferenceStore ps;

	@Override
	public Color getForeground(Object element) {
		if (element instanceof Customer) {
			return getAColor(ps.getString(PREF_CUSTOMER_COLOR));
		}
		if (element instanceof Rental) {
			return getAColor(ps.getString(PREF_RENTAL_COLOR));
		}
		if (element instanceof RentalObject) {
			return getAColor(ps.getString(PREF_RENTAL_OBJECT_COLOR));
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			result = prime * result + ((ra == null) ? 0 : ra.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			if (ra == null) {
				if (other.ra != null)
					return false;
			} else if (!ra.equals(other.ra))
				return false;
			return true;
		}

		private RentalGuiElementProvider getOuterType() {
			return RentalGuiElementProvider.this;
		}
		
	}
	
	private Color getAColor(String rgbKey) {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
		Color col = colorRegistry.get(rgbKey);
		if (col == null) {
			colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
			col = colorRegistry.get(rgbKey);
		}
		return col;
	}
}
