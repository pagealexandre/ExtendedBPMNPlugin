package bpmn2.runtime;

import java.net.URL;

import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.internal.GraphitiUIPlugin;
import org.eclipse.graphiti.ui.platform.AbstractImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

public class ImageManager extends AbstractImageProvider {

	public final static String IMAGE_ID_PREFIX = ImageManager.class.getPackage().getName() + ".";
	public final static String ICONS_FOLDER = "icons/";
	
	public enum IconSize {
		SMALL("small"),
		LARGE("large"),
		HUGE("huge");
		private String value;
		private IconSize(String value) {
			this.value = value;
		}
	}
	
	private static boolean registered = false;
	
	public ImageManager() {
		super();
	}
	
	public static void registerAvailableImages() {
		if (!registered) {
			ImageRegistry imageRegistry = GraphitiUIPlugin.getDefault().getImageRegistry();
			TargetRuntime rt = TargetRuntime.getRuntime(RuntimeExtensionEngine.RUNTIME_ID);
			for (IconSize size : IconSize.values()) {
				for (CustomTaskDescriptor ctd : rt.getCustomTaskDescriptors()) {
					String imageId = getImageId(ctd,size);
					if (imageId != null) {
						String filename = getImagePath(ctd,size);
						URL url = ImageManager.class.getClassLoader().getResource(filename);
						ImageDescriptor descriptor =  ImageDescriptor.createFromURL(url);
						imageRegistry.put(imageId, descriptor);
					}
				}
			}
			registered = true;
		}
	}
	
	@Override
	protected void addAvailableImages() {
		TargetRuntime rt = TargetRuntime.getRuntime(RuntimeExtensionEngine.RUNTIME_ID);
		for (IconSize size : IconSize.values()) {
			for (CustomTaskDescriptor ctd : rt.getCustomTaskDescriptors()) {
				String imageId = getImageId(ctd,size); 
				if (imageId != null) {
					addImageFilePath(imageId, getImagePath(ctd,size));
				}
			}
		}
	}
	
	public static Image createImage(GraphicsAlgorithmContainer ga, CustomTaskDescriptor ctd, int w, int h) {
		// To create an image of a specific size, use the "huge" versions
		// to prevent pixelation when stretching a small image
		String imageId = ImageManager.getImageId(ctd, IconSize.HUGE);
		Image img = null;
		if (imageId != null) {
			img = Graphiti.getGaService().createImage(ga, imageId);
			img.setProportional(false);
			img.setWidth(w);
			img.setHeight(h);
			img.setStretchH(true);
			img.setStretchV(true);
			img.setX(2);
			img.setY(10);
		}
		return img;
	}
	
	public static String getImageId(CustomTaskDescriptor ctd, IconSize small) {
		String icon = (String) ctd.getPropertyValue("icon"); 
		if (icon != null && icon.trim().length() > 0) {
			return IMAGE_ID_PREFIX + icon.trim() + "." + small.value;
		}
		return null;
	}
	
	public static String getImagePath(CustomTaskDescriptor ctd, IconSize size) {
		String icon = (String) ctd.getPropertyValue("icon"); 
		if (icon != null && icon.trim().length() > 0) {
			return ICONS_FOLDER + size.value + "/" + icon.trim();
		}
		return null;
	}
	
}
