setlocal EnableDelayedExpansion

set "PRIVATE_KEY="
set LF=^


rem *** Two empty lines are required for the linefeed
FOR /F "delims=" %%a in (secure\cert\private.pem) do (
  set "PRIVATE_KEY=!PRIVATE_KEY!!LF!%%a"
)
heroku config:add SOME_PRIVATE_KEY="!PRIVATE_KEY!"