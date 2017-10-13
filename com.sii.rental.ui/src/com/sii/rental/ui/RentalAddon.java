package com.sii.rental.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalFactory;
import com.opcoach.training.rental.core.helpers.RentalAgencyGenerator;

public class RentalAddon implements RentalUIConstants {
	
	private Map<String, Palette> paletteManager = new HashMap<>();

	@PostConstruct
	public void init(IEclipseContext ctx, IExtensionRegistry reg) {
		RentalAgency a = RentalAgencyGenerator.createSampleAgency();
		Customer moi = RentalFactory.eINSTANCE.createCustomer();
		moi.setFirstName("Jean-Luc");
		moi.setLastName("Delarbre");
		a.addCustomer(moi);
		ctx.set(RentalAgency.class, a);
		
		ctx.set(RENTAL_UI_IMG_REGISTRY, getLocalImageRegistry());
		ScopedPreferenceStore prefStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);
		ctx.set(RENTAL_UI_PREF_STORE, prefStore);
		
//		String stringDefault = "default";
//		String stringMoche = "moche";
//		paletteManager.put(stringDefault, new Palette(stringDefault, stringDefault, new DefaultPalette()));
//		paletteManager.put(stringMoche, new Palette(stringMoche, stringMoche, new MochePalette()));
		
		getPaletteExtensions(reg, ctx);
		
		ctx.set(PALETTE_MANAGER, paletteManager);
		
		String palId = prefStore.getString(PREF_PALETTE);
		ctx.set(Palette.class, paletteManager.get(palId));
	}
	
	public void getPaletteExtensions(IExtensionRegistry reg, IEclipseContext ctx) {
		for (IConfigurationElement elt : reg.getConfigurationElementsFor("com.sii.rental.ui.palette")) {
			String attributeId = elt.getAttribute("id");
			System.out.println("Found palette: " + attributeId);
			String attributeClass = elt.getAttribute("paletteClass");
			Bundle b = Platform.getBundle(elt.getNamespaceIdentifier());
			Class<?> clazz = null;
			try {
				clazz = b.loadClass(attributeClass);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IColorProvider provider = (IColorProvider) ContextInjectionFactory.make(clazz, ctx);
			Palette palette = new Palette(attributeId, elt.getAttribute("name"), provider);
			paletteManager.put(attributeId, palette);
			System.out.println("Add palette: " + attributeId);
		}
	}
	
	ImageRegistry getLocalImageRegistry()
	{
		// Get the bundle using the universal method to get it from the current class
		Bundle b = FrameworkUtil.getBundle(getClass());  
		
		// Create a local image registry
		ImageRegistry reg = new ImageRegistry();

		// Then fill the values...
		reg.put(IMG_CUSTOMER, ImageDescriptor.createFromURL(b.getEntry(IMG_CUSTOMER)));
		reg.put(IMG_RENTAL, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL)));
		reg.put(IMG_RENTAL_OBJECT, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL_OBJECT)));
		reg.put(IMG_AGENCY, ImageDescriptor.createFromURL(b.getEntry(IMG_AGENCY)));
		reg.put(IMG_COLLAPSE_ALL, ImageDescriptor.createFromURL(b.getEntry(IMG_COLLAPSE_ALL)));
		reg.put(IMG_EXPAND_ALL, ImageDescriptor.createFromURL(b.getEntry(IMG_EXPAND_ALL)));

		return reg;
	}
	
	@Inject
	@Optional
	public void reactOnCopy(@UIEventTopic("copyCustomer") Customer c) {
		System.out.println("Customer copied: " + c.getDisplayName());
	}
	
//	@Inject
//	public void getExtensionsQuickAccess(IExtensionRegistry reg) {
//		for (IConfigurationElement elt : reg.getConfigurationElementsFor("org.eclipse.e4.workbench.model")) {
//			if (elt.getName().compareTo("fragment") == 0) {
//				String attValue = elt.getAttribute("uri");
//				System.out.println("Found: " + elt.getName() + " with uri: " + attValue + " in plugin: " + elt.getNamespaceIdentifier());
//			}
//			else if (elt.getName().compareTo("processor") == 0) {
//				String attValue = elt.getAttribute("class");
//				System.out.println("Found: " + elt.getName() + " with class: " + attValue + " in plugin: " + elt.getNamespaceIdentifier());
//			}
//		}
//	}
	
	@Inject
	public void changePalette(@Preference(value = PREF_PALETTE) String paletteID, IEclipseContext ctx) {
		if (paletteManager != null) {
			ctx.set(Palette.class, paletteManager.get(paletteID));
		}
	}
}
