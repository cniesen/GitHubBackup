GitHub Backup
=============

Backup all GitHub repository of a GitHub user.  Initial backup is performed via a `git clone --mirror`.  Subsequent
updates are done via `git remote update` in order to avoid having to download  large repositories over and over again.

Where to find the official version?
-----------------------------------

End users can download GitHub Backup at https://github.com/cniesen/GitHubBackup/releases

Developers can contribute to GitHub Backup at https://github.com/cniesen/GitHubBackup

Where to file bugs and feature requests?
----------------------------------------

Please use the issue tracking at https://github.com/cniesen/GitHubBackup/issues

How to use GitHub Backup?
-------------------------

Run `java -jar githubbackup.jar <repo-owner>' where <repo-owner> is the GitHub user whose repositories should be
backed up.  Each backed up repository will be stored as a file in the working directory (most likely the directory
that contains the githubbackup.jar).

How to restore/clone from a GitHub Backup?
------------------------------------------

Simply clone via `git clone file://<backup-dir> <new-repo-dir>`.