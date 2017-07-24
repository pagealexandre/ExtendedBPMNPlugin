package extendingruntime;

import org.eclipse.bpmn2.modeler.core.features.CustomShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.custom.ICustomFeature;

public class SecureCallActivityFeatureContainer extends CustomShapeFeatureContainer {

 // these values must match what's in the plugin.xml
 private final static String TYPE_VALUE = "SecureCallActivity";
 private final static String CUSTOM_CALL_ACTIVITY_ID = "ExtendingRuntime.secureCallActivity";

 public SecureCallActivityFeatureContainer() {
	 
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
