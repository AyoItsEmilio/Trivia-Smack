"""
run_user_interface_tests.py
"""
import unittest
import sys
from web_app.tests.interface.UserInterfaceTest import UserInterfaceTest


def main():
    user_interface_test_suite =\
        unittest.TestLoader().loadTestsFromTestCase(UserInterfaceTest)

    all_suites = unittest.TestSuite([user_interface_test_suite])

    runner = unittest.TextTestRunner()
    result = runner.run(all_suites)

    if result.failures:
        sys.exit(1)

if __name__ == "__main__":
    main()
    sys.exit(0)
