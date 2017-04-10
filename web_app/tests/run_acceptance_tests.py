"""
run_acceptance_tests.py
"""
import unittest
import sys
from web_app.tests.acceptance.AcceptanceTest import AcceptanceTest


def main():
    user_interface_test_suite =\
        unittest.TestLoader().loadTestsFromTestCase(AcceptanceTest)

    all_suites = unittest.TestSuite([user_interface_test_suite])

    runner = unittest.TextTestRunner()
    result = runner.run(all_suites)

    if result.failures:
        sys.exit(1)

if __name__ == "__main__":
    main()
    sys.exit(0)
