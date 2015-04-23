import subprocess
import sys
import setup_util
import os

def start(args, logfile, errfile):
  try:
    subprocess.check_call("./gradlew clean build", shell=True, cwd="jawn-standalone", stderr=errfile, stdout=logfile)
    subprocess.Popen("./gradlew run", cwd="jawn-standalone", shell=True, stderr=errfile, stdout=logfile)
    return 0
  except subprocess.CalledProcessError:
    return 1

def stop(logfile, errfile):
  p = subprocess.Popen(['ps', 'aux'], stdout=subprocess.PIPE)
  out, err = p.communicate()
  for line in out.splitlines():
    if 'jawn-standalone' in line:
      pid = int(line.split(None, 2)[1])
      os.kill(pid, 15)
  return 0