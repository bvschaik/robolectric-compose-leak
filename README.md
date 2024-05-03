# Robolectric + Compose + many tests = slowdown

When using Compose and Robolectric to test it, each activity start is slower than the previous one. This is caused by _something_ hanging around after every test run, increasing the number of Looper messages that need to be executed at activity stat every time.

In the example unit test, which runs the same test 1000 times, the activity start in the last test will process 121372 messages before it's fully started.

Reproducer for: https://github.com/robolectric/robolectric/issues/9043
