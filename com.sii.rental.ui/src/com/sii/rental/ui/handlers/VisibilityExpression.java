 
package com.sii.rental.ui.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

import com.opcoach.training.rental.Customer;

public class VisibilityExpression {
	@Evaluate
	public boolean evaluate(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Object selectedObject) {
		return selectedObject instanceof Customer;
	}
}
