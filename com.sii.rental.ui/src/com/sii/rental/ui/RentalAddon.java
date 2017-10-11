package com.sii.rental.ui;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.core.helpers.RentalAgencyGenerator;

public class RentalAddon {

	@PostConstruct
	public void init(IEclipseContext ctx) {
		ctx.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
	}
}
