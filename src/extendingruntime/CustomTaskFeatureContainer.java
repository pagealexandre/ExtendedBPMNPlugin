package extendingruntime;

import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.features.CustomShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.IShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.task.AddTaskFeature;
import org.eclipse.bpmn2.modeler.core.features.artifact.UpdateTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.TaskFeatureContainer;
import org.eclipse.bpmn2.modeler.ui.features.artifact.CreateTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.ui.features.artifact.TextAnnotationFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.ColorConstant;

import extendingruntime.ImageManager.IconSize;

public class CustomTaskFeatureContainer extends CustomShapeFeatureContainer {

 // these values must match what's in the plugin.xml
 private final static String TYPE_VALUE = "CustomTask";
 private final static String CUSTOM_CALL_ACTIVITY_ID = "ExtendingRuntime.customTask";
 public final static String IMAGE_ID_PREFIX = CustomTaskFeatureContainer.class.getPackage().getName() + ".";

 public CustomTaskFeatureContainer() {
	 
 }

 @Override
 public String getId(EObject object) {
	 EStructuralFeature f = ModelDecorator.getAnyAttribute(object, "type");
	 if (f!=null) {
		 Object id = object.eGet(f);
		 if (TYPE_VALUE.equals(id))
		 return CUSTOM_CALL_ACTIVITY_ID;
	 }
 
	 return null;
 }

 @Override
 public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
	 return new ICustomFeature[] {new ShowPropertiesFeature(fp)};
 }
 
}