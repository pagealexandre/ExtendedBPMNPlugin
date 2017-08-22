package bpmn2.runtime;

import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.features.CustomConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.IFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.ui.features.flow.SequenceFlowFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.util.IColorConstant;

public class SecureSequenceFlowFeatureContainer extends CustomConnectionFeatureContainer {
	
	 // these values must match what's in the plugin.xml
	 private final static String TYPE_VALUE = "SecureSequenceFlow";
	 private final static String SEQUENCE_FLOW_ID = "ExtendingRuntime.secureSequenceFlow";
	 
	 public SecureSequenceFlowFeatureContainer() {
		 
	 }
	 
	@Override
	protected IFeatureContainer createFeatureContainer(IFeatureProvider fp) {
		return new SequenceFlowFeatureContainer()
		{
	
			@Override
			public IAddFeature getAddFeature(IFeatureProvider fp) {
				return new AddSequenceFlowFeature(fp) {
	
					/* 
					 * This implementation of SequenceFlow's decorateConnection() changes the appearance of the connection
					 * to distinguish it from regular SequenceFlows.
					 */
					@Override
					protected void decorateConnection(IAddConnectionContext context, Connection connection,
							SequenceFlow businessObject) {
						super.decorateConnection(context, connection, businessObject);
						connection.getGraphicsAlgorithm().setLineWidth(3);
						connection.getGraphicsAlgorithm().setLineStyle(LineStyle.DASH);
						connection.getGraphicsAlgorithm().setForeground(manageColor(IColorConstant.RED));
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
			 return SEQUENCE_FLOW_ID;
		 }
	 
		 return null;
	 }
	 

	 @Override
	 public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		 return new ICustomFeature[] {new ShowPropertiesFeature(fp)};
	 }

}
