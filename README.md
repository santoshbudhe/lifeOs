LifeOS
LifeOS is an open-source, AI-assisted personal habit and life-tracking project designed around natural conversation, initially through WhatsApp.
The goal is to make daily self-tracking feel less like filling out forms and more like talking to an assistant. A user can describe what happened during the day in ordinary language, while LifeOS organizes that information into useful context about activities, food, routines, goals, behavior, energy, and progress over time.
Vision
Most habit trackers depend on manual forms, checkboxes, and isolated statistics. LifeOS is being built around a different idea: let people communicate naturally, preserve useful long-term context, and use AI to help identify patterns that may otherwise be difficult to notice.
The project is intended to support workflows such as:
logging daily activities and routines;
tracking food and related nutritional information;
tracking goals, habits, behaviors, and progress;
maintaining conversational context over time;
generating daily summaries and follow-ups;
producing personalized nudges and useful behavioral insights;
helping users reflect on patterns and make more intentional choices.
LifeOS is an early-stage project and is under active development. It is not a medical, diagnostic, or mental-health treatment service.
Current project structure
The backend currently contains services for areas including:
activity and calorie tracking;
behavior tracking;
food logging and nutrition;
goals and routines;
daily summaries;
energy tracking;
feedback and follow-ups;
interventions and nudges;
rewards and risk-related logic;
conversation state and context extraction;
WhatsApp integration.
The repository also contains an active development roadmap for an AI conversation pipeline, including components such as user context, insight modules, a pipeline executor, intent classification, routing, logging, tracking insights, response composition, and conversation orchestration.
AI direction
LifeOS is being designed so that an LLM can act as an intelligence layer on top of structured tracking data and conversational history. The intended AI workflow includes:
understanding a user's free-form message;
identifying the user's intent;
extracting information that should be logged;
retrieving relevant historical context;
identifying useful patterns or insights;
generating a helpful response or nudge;
preserving appropriate context for future conversations.
OpenAI models are one of the intended AI options for this layer. The project is still evolving, and the AI architecture will continue to be refined as development progresses.
High-level architecture
User
  |
  v
WhatsApp / Twilio
  |
  v
Spring Boot API
  |
  +--> Conversation & context pipeline
  |
  +--> Tracking services
  |      |-- Activities
  |      |-- Food / nutrition
  |      |-- Behaviors
  |      |-- Goals
  |      |-- Routines
  |      `-- Daily summaries
  |
  +--> PostgreSQL
  |
  `--> AI / LLM insight and response layer
             |
             v
        Personalized response
Technology
Java 21
Spring Boot 3.x
Maven
Spring Data JPA
PostgreSQL
Twilio / WhatsApp integration
LLM-assisted conversational and insight pipeline (under active development)
Getting started
Prerequisites
You will need:
Java 21 or later;
PostgreSQL;
a Twilio account if you want to test WhatsApp functionality;
any external API credentials required by the features you choose to enable.
Clone the repository
git clone https://github.com/santoshbudhe/lifeOs.git
cd lifeOs
Configure environment variables
Do not commit real API keys, passwords, or authentication tokens to the repository. Use environment variables or another secrets-management system.
The application can be configured with variables such as:
DB_URL
DB_USERNAME
DB_PASSWORD
TWILIO_ACCOUNT_SID
TWILIO_AUTH_TOKEN
TWILIO_WHATSAPP_NUMBER
NUTRITION_API_KEY
For local development, use safe local defaults where appropriate and keep all real credentials outside Git.
Run the application
On macOS/Linux:
./mvnw spring-boot:run
On Windows:
mvnw.cmd spring-boot:run
Roadmap
LifeOS is still at an early stage. Planned work includes:
completing the conversational AI orchestration pipeline;
improving intent classification and structured logging from natural language;
building richer longitudinal habit and behavior insights;
improving personalized nudges, follow-ups, and summaries;
strengthening privacy, security, and user-data controls;
adding automated tests and developer documentation;
making AI-provider integration easier to configure;
improving deployment and contributor workflows.
See the repository's GitHub Issues for current implementation tasks.
Contributing
LifeOS is open source and contributions are welcome. You can fork the repository, improve the code or documentation, and submit a pull request.
Because the project is still evolving, substantial architectural changes should be coordinated with the maintainer before implementation.
Privacy and security
LifeOS may eventually handle highly personal daily-life information. Contributors and deployers should treat privacy and security as core design requirements.
Please:
never commit API keys, passwords, authentication tokens, or real user data;
keep secrets in environment variables or a secure secrets manager;
minimize collection of unnecessary personal data;
consider encryption, access controls, retention, export, and deletion when adding data features;
avoid presenting AI-generated insights as professional medical or mental-health advice.
Maintainer
Maintained by Santosh Budhe.
License
Licensed under the Apache License 2.0. See the LICENSE file for details.
