"""
run_integration_tests.py
"""
import unittest
import sys
from web_app.tests.integration.BusinessPersistenceSeamTest\
import BusinessPersistenceSeamTest

def main():

    business_seam_suite =\
    unittest.TestLoader().loadTestsFromTestCase(BusinessPersistenceSeamTest)

    all_suites = unittest.TestSuite([business_seam_suite])

    runner = unittest.TextTestRunner()
    result = runner.run(all_suites)

    if result.failures:
        sys.exit(1)

if __name__ == "__main__":
    main()
    sys.exit(0)

