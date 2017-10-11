package com.sii.rental.ui;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalFactory;
import com.opcoach.training.rental.core.helpers.RentalAgencyGenerator;

public class RentalAddon {

	@PostConstruct
	public void init(IEclipseContext ctx) {
		RentalAgency a = RentalAgencyGenerator.createSampleAgency();
		Customer moi = RentalFactory.eINSTANCE.createCustomer();
		moi.setFirstName("Jean-Luc");
		moi.setLastName("Delarbre");
		a.addCustomer(moi);
		ctx.set(RentalAgency.class, a);
	}
}
