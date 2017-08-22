package bpmn2.runtime;

import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.bpmn2.modeler.ui.AbstractBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.ui.wizards.FileService;
import org.eclipse.ui.IEditorInput;
import org.xml.sax.InputSource;

public class RuntimeExtensionEngine extends AbstractBpmn2RuntimeExtension {

	public static final String RUNTIME_ID = "uk.ac.kent.bpmn2.runtime";

	public static final String targetNamespace = "https://www.kent.ac.uk";

	@Override
	public String getTargetNamespace(Bpmn2DiagramType diagramType) {
		return targetNamespace;
	}

	/**
	 * IMPORTANT: The plugin is responsible for inspecting the file contents!
	 * Unless you are absolutely sure that the file is targeted for this runtime
	 * (by, e.g. looking at the targetNamespace or some other feature) then this
	 * method must return FALSE.
	 */
	@Override
	public boolean isContentForRuntime(IEditorInput input) {
		InputSource source = new InputSource(
				FileService.getInputContents(input));
		RootElementParser parser = new RootElementParser(
				"https://www.kent.ac.uk");
		parser.parse(source);
		return parser.getResult();
	}

}