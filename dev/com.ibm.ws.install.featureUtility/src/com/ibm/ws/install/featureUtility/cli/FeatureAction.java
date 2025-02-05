/*******************************************************************************
 * Copyright (c) 2019, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.install.featureUtility.cli;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import com.ibm.ws.install.InstallKernel;
import com.ibm.ws.install.InstallKernelFactory;
import com.ibm.ws.install.internal.InstallKernelImpl;
import com.ibm.ws.kernel.boot.cmdline.ActionDefinition;
import com.ibm.ws.kernel.boot.cmdline.ActionHandler;
import com.ibm.ws.kernel.boot.cmdline.Arguments;
import com.ibm.ws.kernel.boot.cmdline.ExitCode;
import com.ibm.ws.kernel.feature.internal.cmdline.ArgumentsImpl;
import com.ibm.ws.kernel.feature.internal.cmdline.FeatureToolException;
import com.ibm.ws.kernel.feature.internal.cmdline.NLS;
import com.ibm.ws.kernel.feature.internal.cmdline.ReturnCode;


public enum FeatureAction implements ActionDefinition {
    installFeature(new InstallFeatureAction(), "if", -1, "--noCache", "--verbose", "--acceptLicense", "--featuresBom",
	    "--to", "name..."),
    installServerFeatures(new InstallServerAction(), "isf", -1, "--noCache", "--verbose", "--acceptLicense",
	    "--featuresBom", "name..."),
    viewSettings(new ViewSettingsAction(),"", 0, "--viewValidationMessages"),
    find(new FindAction(), "", -1, "[searchString]"),
    help(new FeatureHelpAction(),"", 0);

    private List<String> commandOptions;
    private ActionHandler action;
    private int positionalOptions;
    private String abbreviation;

    private FeatureAction(ActionHandler a, String abbreviationString, int count, String... args) {
        
        action = a;
        positionalOptions = count;
        abbreviation = abbreviationString;

	// For beta FAT test
	boolean enableVerify = System.getProperty("enable.verify") != null
		&& System.getProperty("enable.verify").equals("true");
	if (enableVerify) {
	    if (abbreviationString.equals("if")) {
		commandOptions = Collections.unmodifiableList(Arrays.asList("--noCache", "--verbose",
			    "--acceptLicense", "--featuresBom", "--to", "--verify", "name..."));
	    } else if (abbreviationString.equals("isf")) {
		commandOptions = Collections.unmodifiableList(Arrays.asList("--noCache", "--verbose",
			    "--acceptLicense", "--featuresBom", "--verify", "name..."));
	    } else {
		commandOptions = Collections.unmodifiableList(Arrays.asList(args));
	    }

	} else {
	    commandOptions = Collections.unmodifiableList(Arrays.asList(args));
	}

    }

    @Override
    public List<String> getCommandOptions() {
        return commandOptions;
    }

    @Override
    public int numPositionalArgs() {
        return positionalOptions;
    }

    public String getAbbreviation(){
        return abbreviation;
    }

    public static FeatureAction getEnum(String name){
        for (FeatureAction action : values()){
            if(action.toString().equals(name) || action.getAbbreviation().equals(name)){
                return action;
            }
        }

        throw new IllegalArgumentException(name);
    }

    @Override
    public ExitCode handleTask(Arguments args) {
        try {
            // Set log level if --verbose specified
            InstallKernel installKernel = InstallKernelFactory.getInstance();
            String verboseLevel = args.getOption("verbose");
            
            Level logLevel = Level.INFO;
            if (verboseLevel != null && verboseLevel.isEmpty()) {
                logLevel = Level.FINEST;
            } else if (verboseLevel != null && !verboseLevel.isEmpty()) {
            	System.out.println(NLS.getMessage("unknown.options", args.getAction(), "--verbose=" + verboseLevel));
                FeatureAction.help.handleTask(new ArgumentsImpl(new String[] { "help", FeatureAction.getEnum(args.getAction()).toString() }));
                return ReturnCode.BAD_ARGUMENT;
            }
          
            ((InstallKernelImpl) installKernel).enableConsoleLog(logLevel);
            return action.handleTask(System.out, System.err, args);
        } catch (FeatureToolException fte) {
            System.err.println(fte.getMessage());
            fte.printStackTrace();
            return fte.getReturnCode();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ReturnCode.RUNTIME_EXCEPTION;
        }
    }

    /**
     * Finds all values from CommandOptions and returns if it is a command
     *
     * @return if option is a command (true)
     */
    public boolean showOptions() {
        List<String> options = getCommandOptions();
        for (String option : options) {
            if (option.startsWith("-"))
                return true;
        }
        return false;
    }

}
