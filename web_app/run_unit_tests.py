"""
run_unit_tests.py
"""
import unittest
import sys
from web_app.tests.objects.QuestionTest import QuestionTest
from web_app.tests.persistence.DataAccessTest import DataAccessTest
from web_app.tests.business.AccessQuestionsTest import AccessQuestionsTest

def main():

    question_suite = unittest.TestLoader().loadTestsFromTestCase(QuestionTest)

    question_access_suite =\
    unittest.TestLoader().loadTestsFromTestCase(AccessQuestionsTest)

    data_access_suite =\
    unittest.TestLoader().loadTestsFromTestCase(DataAccessTest)

    all_suites = unittest.TestSuite([question_suite,\
        data_access_suite,\
        question_access_suite])

    runner = unittest.TextTestRunner()
    result = runner.run(all_suites)

    if result.failures:
        sys.exit(1)

if __name__ == "__main__":
    main()
    sys.exit(0)

