# ExtendedBPMNPlugin

# Introduction

This plugin is an extension to the BPMN Modeler language used for diagram choreography. The main goal of this extension is to provide
secure entities with focus on HTTPS concept, authentication, and restraint access to resources.

# Entities

4 new entities has been created for extending BPMN diagram.

![Extended BPMN](https://github.com/pagealexandre/ExtendedBPMNPlugin/blob/master/pictures/toolPalettes.png)


# Senarios

Two scenarios were created using the secure annotation : [ARS Scenario BPMN](https://github.com/pagealexandre/ExtendedBPMNPlugin/blob/master/pictures/SecureARSReservationScenario.png)
and [Bank transfer money scenario](https://github.com/pagealexandre/ExtendedBPMNPlugin/blob/master/pictures/BankTransferMoney.png)

# Run it.
`git clone https://github.com/pagealexandre/ExtendedBPMNPlugin.git`

- Open the project with eclipse
- Run it with ![button](https://github.com/pagealexandre/ExtendedBPMNPlugin/blob/master/pictures/runButton.png)

- Create an new empty project in the new Eclipse application
- Select the project and press : cmd + i (on MAC)
- Select "BPMN2" Category and choose "Runtime Extension Engine" for Target Runtime
- Copy [BPMN files](https://github.com/pagealexandre/ExtendedBPMNPlugin/tree/master/BPMN) that use the secure annotation
into the newly created project
