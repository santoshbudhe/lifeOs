package com.lifeos.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * TICKET-205: Router
 *
 * Purpose:
 * - Determines which modules should run based on intent
 * - Builds a dynamic execution pipeline
 *
 * Input:
 * - UserContext (must contain intent)
 *
 * Output:
 * - List of InsightModules to execute
 *
 * Notes:
 * - Keeps decision logic separate from execution
 * - Allows flexible flows per intent
 */
@Component
public class Router {

    private static final Logger log = LoggerFactory.getLogger(Router.class);

    /**
     * Build module pipeline based on intent
     */
    public List<InsightModule> route(UserContext context) {

        String intent = context.getIntent();

        log.info("[ROUTER] requestId={} intent={}", context.getRequestId(), intent);

        List<InsightModule> modules = new ArrayList<>();

        if (intent == null) {
            log.warn("[ROUTER] requestId={} No intent found, defaulting to DISCOVERY flow",
                    context.getRequestId());
            intent = "DISCOVERY";
        }

        switch (intent) {

//            case "LOGGING":
//                modules.add(new LoggingModule());
//                modules.add(new TrackingInsightModule());
//                break;
//
//            case "DISCOVERY":
//                modules.add(new ExplorationModule());
//                modules.add(new ProblemPrioritizationModule());
//                break;
//
//            case "QUERY":
//                modules.add(new QueryModule());
//                break;
//
//            case "RESPONSE":
//                modules.add(new ResponseHandlingModule());
//                break;
//
//            default:
//                log.warn("[ROUTER] requestId={} Unknown intent={}, defaulting to DISCOVERY",
//                        context.getRequestId(), intent);
//                modules.add(new ExplorationModule());
       
        case "LOGGING":
            log.info("[ROUTER] LOGGING flow selected");
            break;

        case "DISCOVERY":
            log.info("[ROUTER] DISCOVERY flow selected");
            break;

        case "QUERY":
            log.info("[ROUTER] QUERY flow selected");
            break;

        case "RESPONSE":
            log.info("[ROUTER] RESPONSE flow selected");
            break;

        default:
            log.info("[ROUTER] DEFAULT DISCOVERY flow selected");

        
        }
        
        
        
        

        log.info("[ROUTER] requestId={} modulesSelected={}",
                context.getRequestId(),
                modules.stream().map(InsightModule::getModuleName).toList()
        );

        return modules;
    }
}
