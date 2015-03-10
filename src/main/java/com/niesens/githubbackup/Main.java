package com.niesens.githubbackup;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    private static final String GIT_COMMAND_CLONE = "git clone --mirror ";
    private static final String GIT_COMMAND_CLONE_VERBOSE = "git clone -v --mirror ";
    private static final String GIT_COMMAND_UPDATE = "git remote update ";
    private static final String GIT_COMMAND_UPDATE_VERBOSE = "git remote -v update ";

    public static void main (String[] args) throws IOException {
        ArgumentOptions argumentOptions = new ArgumentOptions(args);

        RepositoryService repositoryService = new RepositoryService();
        List<Repository> repositories = repositoryService.getRepositories(argumentOptions.getOwner());
        for (Repository repository : repositories) {
            System.out.println();

            File localRepository = new File(System.getProperty("user.dir") + "/" + repository.getName() +".git");
            String command = null;
            File workingDirectory = null;
            if (localRepository.exists()) {
                checkGitDirectory(localRepository);
                command = argumentOptions.isVerbose() ? GIT_COMMAND_UPDATE_VERBOSE : GIT_COMMAND_UPDATE;
                workingDirectory = localRepository;
            } else {
                command = argumentOptions.isVerbose() ? GIT_COMMAND_CLONE_VERBOSE + repository.getCloneUrl() : GIT_COMMAND_CLONE + repository.getCloneUrl();
                workingDirectory = new File(System.getProperty("user.dir"));
            }

            System.out.println(workingDirectory + "> " + command);
            if (!argumentOptions.isTestMode()) {
                runCommand(command, workingDirectory);
            }
        }
    }

    private static void runCommand(String command, File workingDirectory) throws IOException {
        Process process = Runtime.getRuntime().exec(command, null, workingDirectory);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String s = null;

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static void checkGitDirectory(File localRepository) {
        if (!localRepository.isDirectory()) {
            System.out.println("Error: " + localRepository.getPath() +" can not be backed up since a file with the same name exists");
            System.exit(0);
        }

        File dotGitDirectory = new File(localRepository.getPath() + "/HEAD");
        if (!dotGitDirectory.exists() || dotGitDirectory.isDirectory()) {
            System.out.println("Error: " + localRepository.getPath() +" can not be backed up since directory is not a git repository");
            System.exit(0);
        }
    }

}
