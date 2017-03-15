"""
run_integration_tests.py
"""
import unittest
import sys
from web_app.tests.integration.BusinessPersistenceSeamTest\
    import BusinessPersistenceSeamTest
from web_app.tests.integration.DataAccessMongoTest import DataAccessMongoTest

def main():

    business_seam_suite =\
        unittest.TestLoader().loadTestsFromTestCase(BusinessPersistenceSeamTest)

    data_access_suite =\
        unittest.TestLoader().loadTestsFromTestCase(DataAccessMongoTest)

    all_suites = unittest.TestSuite([business_seam_suite,\
                                     data_access_suite])

    runner = unittest.TextTestRunner()
    result = runner.run(all_suites)

    if result.failures:
        sys.exit(1)

if __name__ == "__main__":
    main()
    sys.exit(0)

