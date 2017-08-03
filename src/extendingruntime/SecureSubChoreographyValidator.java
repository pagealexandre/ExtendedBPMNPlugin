package extendingruntime;

import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

public class SecureSubChoreographyValidator extends AbstractBpmn2ElementValidator<SubChoreography> {
	
	public SecureSubChoreographyValidator(IValidationContext ctx) {
		
	}

	@Override
	public IStatus validate(SubChoreography object) {
		// TODO Auto-generated method stub
		return null;
	}
}
