
package com.sii.rental.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalObject;
import com.sii.rental.core.RentalCoreActivator;


public class RentalPart {

	private Label rentedObjectLabel;
	private Label lblRentee;
	private Label lblStartDate;
	private Label lblEndDate;
	
	@PostConstruct
	public void createContent(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		infoGroup.setText("Information");
		infoGroup.setLayout(new GridLayout(2, false));

		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		
		Label lblLoueA = new Label(infoGroup, SWT.NONE);
		lblLoueA.setText("Loué à:");
		
		lblRentee = new Label(infoGroup, SWT.NONE);
		
		Group datesgroup = new Group(parent, SWT.NONE);
		datesgroup.setLayout(new GridLayout(2, false));
		
		Label lblDu = new Label(datesgroup, SWT.NONE);
		lblDu.setText("du:");
		
		lblStartDate = new Label(datesgroup, SWT.NONE);
		
		Label lblAu = new Label(datesgroup, SWT.NONE);
		lblAu.setText("au:");
		
		lblEndDate = new Label(datesgroup, SWT.NONE);
		
		setRental(RentalCoreActivator.getAgency().getRentals().get(0));
	}

	@Focus
	public void onFocus() {
		
	}
	
	public void setRental(Rental r)
	{
		RentalObject rentedObject = r.getRentedObject();
		rentedObjectLabel.setText(rentedObject.getName());
		lblRentee.setText(r.getCustomer().getFirstName() + " " + r.getCustomer().getLastName());
		lblStartDate.setText(r.getStartDate().toString());
		lblEndDate.setText(r.getEndDate().toString());
	}
}