# donation-play-2015

ictskills (2015) studio play framework application

Play 1.3.0

Application including assignment solution developed during ICTSkills Summer School (Studio) 2015.

The following scripts placed in .bash_profile facilitate pretty printing of git logs:

Git aliases pretty print logs

#alias gitp='git log --pretty=oneline --max-count=10'

alias gitp="git log --pretty=format:'%C(yellow)%h %<(24)%C(red)%ad  %Creset%s' --date=local --max-count=10"

alias gitpd='git log --pretty=oneline'

alias gitpp="git log --pretty=format:'%C(yellow)%h %<(24)%C(red)%ad  %Creset%s' --date=local"

alias gitag="git log --no-walk --tags --pretty=format:' %C(yellow)%h %<(30)%Cgreen%d  %Cred%ad  %Creset%s' --date=local"

alias gitt="git tag | xargs git tag -d | git pull --tags"

The following includes the author(s) 

alias gitpa="git log --pretty=format:'%C(yellow)%h %<(24)%C(red)%ad   %<(18)%C(green)%an %C(reset)%s' --date=local --max-count=10"

alias gitppa="git log --pretty=format:'%C(yellow)%h %<(24)%C(red)%ad   %<(18)%C(green)%an %C(reset)%s' --date=local"
