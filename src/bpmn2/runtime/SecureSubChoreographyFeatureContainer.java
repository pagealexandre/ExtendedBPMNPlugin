package bpmn2.runtime;

import java.net.URL;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.features.AbstractUpdateBaseElementFeature;
import org.eclipse.bpmn2.modeler.core.features.CustomShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.ui.features.choreography.AddSubChoreographyFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.SubChoreographyFeatureContainer;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.resource.ImageDescriptor;

import bpmn2.runtime.ImageManager.IconSize;

public class SecureSubChoreographyFeatureContainer extends CustomShapeFeatureContainer {
	
	private final static String TYPE_VALUE = "SecureSubChoreography";
	private final static String CUSTOM_CALL_SUB_CHOREOGRAPHY_ID = "ExtendingRuntime.secureSubChoreography";
	private static Boolean First_Time = true;
	 
	public SecureSubChoreographyFeatureContainer() {
		
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
							Image img = CustomTaskImageProvider.createImage(customTaskDescriptor, ga,
										"lock.png", 24, 24);
							Graphiti.getGaService().setLocationAndSize(img, 12, 24, 24, 24);
						}
					};
				}
				
				public MultiUpdateFeature getUpdateFeature(IFeatureProvider fp) {
			 		MultiUpdateFeature multiUpdate = (MultiUpdateFeature) super.getUpdateFeature(fp);
			 		multiUpdate.addFeature(new  AbstractUpdateBaseElementFeature<SubChoreography>(fp) {
			 			
			 			@Override
			 			public boolean update(IUpdateContext context) {
			 				if (First_Time) {
			 					First_Time = false;
			 					registerImage();
			 				}
			 				
			 				ContainerShape containerShape = (ContainerShape)context.getPictogramElement();
							Shape shape = containerShape.getChildren().get(0);
							GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
							
							PictogramElement pe = context.getPictogramElement();
							SubChoreography ta = (SubChoreography) getBusinessObjectForPictogramElement(pe);
							BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(containerShape, BPMNShape.class);
							Bounds bounds = bpmnShape.getBounds();
							
							int width = (int) bounds.getWidth();
							
							shape.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().clear();
							Image img = CustomTaskImageProvider.createImage(customTaskDescriptor, ga, "lock.png", 24, 24);
							Graphiti.getGaService().setLocationAndSize(img, 12, 24, 24, 24);
							Boolean state = false;
							EStructuralFeature f = ModelDecorator.getAnyAttribute(ta, "restraint");
							if (f != null) {
								state = Boolean.parseBoolean(String.valueOf(ta.eGet(f)));
								if (state == true) {
									img = CustomTaskImageProvider.createImage(customTaskDescriptor, ga, "key.png", 24, 24);
									Graphiti.getGaService().setLocationAndSize(img, width - 40, 24, 24, 24);
								}
							}
							
			 				FeatureSupport.setPropertyValue(pe, "evaluate.property", String.valueOf(state));
			 				FeatureSupport.setPropertyValue(pe, "evaluate.width", String.valueOf(width));
			 				return true;
			 			}
			 		
			 			@Override
			 			public IReason updateNeeded(IUpdateContext context) {
			 				IReason reason = super.updateNeeded(context);
			 				if (reason.toBoolean())
			 					return reason;
			 				
			 				PictogramElement pe = context.getPictogramElement();
			 				String myState = FeatureSupport.getPropertyValue(pe, "evaluate.property");
			 				BaseElement ta = (BaseElement) getBusinessObjectForPictogramElement(pe);

	 						EStructuralFeature f = ModelDecorator.getAnyAttribute(ta, "restraint");
			 				if (f != null) {
			 					Boolean newState = Boolean.parseBoolean(String.valueOf(ta.eGet(f)));
				 				if (Boolean.parseBoolean(myState) != newState) {
				 					return Reason.createTrueReason("evaluate.property changed");
				 				}
			 				}
			 				
			 				ContainerShape containerShape = (ContainerShape)context.getPictogramElement();
							BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(containerShape, BPMNShape.class);
							Bounds bounds = bpmnShape.getBounds();
			 				String sessionWidth = FeatureSupport.getPropertyValue(pe, "evaluate.width");
			 				int width = (int) bounds.getWidth();
			 				
			 				if (sessionWidth == null || sessionWidth.isEmpty()) {
			 					sessionWidth = "-1";
			 				}
			 				
		 					if (Integer.parseInt(sessionWidth) != width) {
		 						return Reason.createTrueReason("evaluate.width changed");
		 					}
			 				return Reason.createFalseReason("");
			 			}
			 		});
			 		
			 		return multiUpdate;
			 	}
				
				private void registerImage() {
					String imageId = customTaskDescriptor.getImageId("key.png", CustomTaskImageProvider.IconSize.LARGE);
					String filename = "/icons/large/" + "key.png";
					URL url = getClass().getClassLoader().getResource(filename);
					ImageDescriptor.createFromURL(url);
					ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
					CustomTaskImageProvider.registerImage(imageId, descriptor);
				}
				
			};
	 }
	 
	 @Override
	 public String getId(EObject object) {
		 EStructuralFeature f = ModelDecorator.getAnyAttribute(object, "type");
		 if (f!=null) {
			 Object id = object.eGet(f);
			 if (TYPE_VALUE.equals(id))
			 return CUSTOM_CALL_SUB_CHOREOGRAPHY_ID;
		 }
	 
		 return null;
	 }

	 @Override
	 public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		 return new ICustomFeature[] {new ShowPropertiesFeature(fp)};
	 }
	
	
}
