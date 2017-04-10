"""
AcceptanceTest.py
"""
import unittest
import time
import random
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException


class AcceptanceTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.driver = webdriver.Chrome()
        cls.driver.implicitly_wait(5)


    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()


    def test_title(self):
        print "Testing for correct title."

        self.driver.get("http://0.0.0.0:5000/index.html")
        self.assertEquals("Trivia Smack!", self.driver.title)


    def test_click_one_player(self):
        print "Testing clicking the \"One Player\" button"

        self.driver.get("http://0.0.0.0:5000/index.html")
        one_player_button = self.driver.find_element_by_xpath(
            "//div[text()='One Player!']")
        one_player_button.click()

        print "\tButton Clicked, test that it went to a question page."
        text_displayed = self.driver.find_element_by_xpath("//body").text
        question = self.driver.find_element_by_xpath(
            "//div[@id='main']/div[1]/h3[@data-bind=\"text: question\"]").text
        options = self.driver.find_elements_by_xpath(
            "//div[@id='main']/div[1]/div")

        print "\t\tTest that a question is being displayed"
        self.assertIn(question, text_displayed)

        print "\t\tTest that options are being displayed"
        for option in options:
            option = option.text
            self.assertIn(option, text_displayed)


    def test_click_option_1p(self):
        print "Testing clicking an option in One Player mode"

        self.driver.get("http://0.0.0.0:5000/index.html")
        one_player_button = self.driver.find_element_by_xpath(
            "//div[text()='One Player!']")
        one_player_button.click()

        options = self.driver.find_elements_by_xpath(
            "//div[@id='main']/div[1]/div")
        selected_option = random.randint(0, len(options)-1)
        options[selected_option].click()


    def test_round_1p(self):
        print "Testing going through one game in 1 player mode"

        self.driver.get("http://0.0.0.0:5000/index.html")
        time.sleep(2)
        one_player_button = self.driver.find_element_by_xpath(
            "//div[text()='One Player!']")
        one_player_button.click()

        time.sleep(2)

        question = self.driver.find_element_by_xpath(
            "//div[@id='main']/div[1]/h3[@data-bind=\"text: question\"]").text

        while question:
            print '\tTesting going through questions.'

            time.sleep(2)
            options = self.driver.find_elements_by_xpath(
                "//div[@id='main']/div[1]/div")
            selected_option = random.randint(0, len(options)-1)
            time.sleep(2)
            options[selected_option].click()
            time.sleep(2)

            try:
                question = self.driver.find_element_by_xpath(
                    "//div[@id='main']/div[1]/h3"
                    "[@data-bind=\"text: question\"]").text
            except NoSuchElementException:
                print '\tReached end of round.'
                question = None

        print '\tTest that score is displayed at end of round.'
        text_displayed = self.driver.find_element_by_xpath("//body").text
        self.assertIn("Score:", text_displayed)


    def test_wait_2p(self):
        print "Test clicking Two Player button brings user to waiting page."

        self.driver.get("http://0.0.0.0:5000/index.html")
        time.sleep(2)
        two_player_button = self.driver.find_element_by_xpath(
            "//div[text()='Two Player!']")
        two_player_button.click()
        time.sleep(3)
        text_displayed = self.driver.find_element_by_xpath("//body").text
        waiting_text = "Waiting for other player..."
        self.assertIn(waiting_text, text_displayed)


    def test_connect_2p(self):
        print "Test connecting 2 players."

        default_handle = self.driver.current_window_handle
        self.driver.get("http://0.0.0.0:5000/index.html")
        time.sleep(2)
        two_player_button = self.driver.find_element_by_xpath(
            "//div[text()='Two Player!']")
        two_player_button.click()
        time.sleep(10)

        self.driver.execute_script("window.open()")
        self.driver.switch_to_window(self.driver.window_handles[1])
        self.driver.get("http://0.0.0.0:5000/index.html")
        time.sleep(2)
        two_player_button = self.driver.find_element_by_xpath(
            "//div[text()='Two Player!']")
        two_player_button.click()
        time.sleep(3)

        self.driver.switch_to_window(self.driver.window_handles[0])
        print "\tTest player 1 was brought to question page after connecting."

        try:
            question_p1 = self.driver.find_element_by_xpath(
                "//div[@id='main']/div[1]/h3"
                "[@data-bind=\"text: question\"]").text
        except NoSuchElementException:
            question_p1 = None

        self.assertIsNotNone(question_p1)

        self.driver.switch_to_window(self.driver.window_handles[1])
        print "\tTest player 2 was brought to question page after connecting."

        try:
            question_p2 = self.driver.find_element_by_xpath(
                "//div[@id='main']/div[1]/h3"
                "[@data-bind=\"text: question\"]").text
        except NoSuchElementException:
            question_p2 = None

        self.assertIsNotNone(question_p2)
        self.driver.close()
        self.driver.switch_to_window(default_handle)
