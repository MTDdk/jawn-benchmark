# jawn-standalone

https://www.techempower.com/benchmarks/#section=data-r10&hw=peak&test=fortune
https://github.com/TechEmpower/FrameworkBenchmarks

## Problems with the mounting of FrameworkBenchmarks?

* **On host**:     vagrant gem install vagrant-vbguest
* **In guest OS**: sudo mount.vboxsf -o uid=`id -u vagrant`,gid=`getent group vagrant | cut -d: -f3`,dmode=777,fmode=777 FrameworkBenchmarks /FrameworkBenchmarks

toolset/run-tests.py --install server --test jawn-standalone --verbose --install-only

toolset/run-tests.py --test jawn-standalone --mode verify

toolset/run-tests.py --test jawn-standalone

toolset/run-tests.py --test jawn-standalone --type fortune --sleep 10