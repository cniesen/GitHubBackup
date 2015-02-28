package com.niesens.githubbackup;

public class ArgumentOptions {
    private static final String PROGRAM_INFO = "\nGitHubBackup Ver. 1.0\n"
            + "https://github.com/cniesen/GitHubBackup\n"
            + "\n"
            + "Creates a local backup (mirror) of all repositories for a user.  It does so via\n"
            + "'git clone --mirror' and then keeps these backups up to date via 'git remote update'.\n"
            + "\n"
            + "Usage: java -jar githubbackup.jar [options] <repo-owner>\n"
            + "\n"
            + "The <repo-owner> is required and specifies whose repositories should be backed up.\n"
            + "\n"
            + "The following arguments are optional:\n"
            + "    -v            Verbose.\n"
            + "    -t            Test run. Displays commands but does not execute them.\n"
            + "    -u username   GitHub login. In order to use higher GitHub API rate limits.\n"
            + "                  Not yet implemented.\n"
            + "    -p password   GitHub password. Required with the -u flag.\n"
            + "\n";


    private String owner = null;
    private String user = null;
    private String password = null;
    private boolean verbose = false;
    private boolean testMode = false;

    public ArgumentOptions(String[] args) {
        if (args.length ==0) {
            System.out.println(PROGRAM_INFO);
            System.exit(0);
        }

        for(int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) {
                if (owner != null) {
                    System.out.println("Error: Owner specified multiple times");
                    System.exit(0);
                } else {
                    owner = args[i];
                }
            } else if (args[i].equals("-u")) {
                i++;
                if ((i < args.length) || (args[i].startsWith("-"))) {
                    System.out.println("Error: Missing username with option '-u'");
                    System.exit(0);
                } else {
                    if (user != null) {
                        System.out.println("Error: User specified multiple times");
                        System.exit(0);
                    } else {
                        user = args[i];
                    }
                }
            } else if (args[i].equals("-p")) {
                i++;
                if ((i < args.length) || (args[i].startsWith("-"))) {
                    System.out.println("Error: Missing password with option '-p'");
                    System.exit(0);
                } else {
                    if (password != null) {
                        System.out.println("Error: Password specified multiple times");
                        System.exit(0);
                    } else {
                        password = args[i];
                    }
                }
            } else if (args[i].equals("-v")) {
                verbose = true;
            } else if (args[i].equals("-t")) {
                testMode = true;
           } else {
                System.out.println("Error: Unknown option '" + args[i] + "'");
                System.exit(0);
            }
        }
    }

    public String getOwner() {
        return owner;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isTestMode() {
        return testMode;
    }
}
