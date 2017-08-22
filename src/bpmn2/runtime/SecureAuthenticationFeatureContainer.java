package bpmn2.runtime;

import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.modeler.core.features.CustomShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider;
import org.eclipse.bpmn2.modeler.ui.features.choreography.AddSubChoreographyFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.SubChoreographyFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

import bpmn2.runtime.ImageManager.IconSize;

public class SecureAuthenticationFeatureContainer extends CustomShapeFeatureContainer {
	
	private final static String TYPE_VALUE = "SecureAuthentication";
	private final static String CUSTOM_CALL_LOGIN_ID = "ExtendingRuntime.secureAuth";
	 
	public SecureAuthenticationFeatureContainer() {
		
	}
	
	 @Override
	 protected SubChoreographyFeatureContainer createFeatureContainer(IFeatureProvider fp) {
			return new SubChoreographyFeatureContainer() {
				
				@Override
				public ICreateFeature getCreateFeature(IFeatureProvider fp) {
					return new CreateSubChoreographyFeature(fp) {
						@Override
						public String getCreateImageId() {
							return ImageManager.getImageId(customTaskDescriptor, IconSize.SMALL);
						}
						
					};

				}
				
				@Override
				public IAddFeature getAddFeature(IFeatureProvider fp) {
					return new AddSubChoreographyFeature(fp) {
						@Override
						protected void decorateShape(IAddContext context, 
					 	 	 	ContainerShape containerShape, SubChoreography businessObject) {
							super.decorateShape(context, containerShape, businessObject);
							Shape shape = containerShape.getChildren().get(0);
							GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
							Image img = CustomTaskImageProvider.createImage(customTaskDescriptor, ga, "login.png", 24, 24);
							Graphiti.getGaService().setLocationAndSize(img, 12, 24, 24, 24);
						}
					};
				}
			};
	 }
	 
	 @Override
	 public String getId(EObject object) {
		 EStructuralFeature f = ModelDecorator.getAnyAttribute(object, "type");
		 if (f!=null) {
			 Object id = object.eGet(f);
			 if (TYPE_VALUE.equals(id))
			 return CUSTOM_CALL_LOGIN_ID;
		 }
	 
		 return null;
	 }

	 @Override
	 public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		 return new ICustomFeature[] {new ShowPropertiesFeature(fp)};
	 }
	
	
}
