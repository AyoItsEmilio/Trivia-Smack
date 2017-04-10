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


    def test_click_option_1p(self):
        print "Testing clicking an option in One Player mode"

        self.driver.get("http://0.0.0.0:5000/index.html")
        one_player_button = self.driver.find_element_by_xpath("//*[@id='main']/div[2]/div[1]")
        one_player_button.click()

        options = self.driver.find_elements_by_xpath("//*[@id='category']/div[2]")
        selected_option = random.randint(0, len(options)-1)
        options[selected_option].click()


    def test_all_categories_option_1p(self):
        print "Testing clicking all the categories in one player mode"

        self.driver.get("http://0.0.0.0:5000/index.html")
        one_player_button = self.driver.find_element_by_xpath("//*[@id='main']/div[2]/div[1]")
        one_player_button.click()

        options = self.driver.find_elements_by_xpath("//*[@id='category']/div[2]")
        selected_option = random.randint(0, len(options)-1)
        options[selected_option].click()

        count = 3
        while (count < 7):
            time.sleep(2)
            self.driver.get("http://0.0.0.0:5000/index.html")
            one_player_button = self.driver.find_element_by_xpath("//*[@id='main']/div[2]/div[1]")
            one_player_button.click()

            xpath = "//*[@id='category']/div["+`count`+"]"
            options = self.driver.find_elements_by_xpath(xpath)
            selected_option = random.randint(0, len(options)-1)
            options[selected_option].click()
            count = count + 1

    def test_1p_game(self):
        print "Test one game"

        self.driver.get("http://0.0.0.0:5000/index.html")
        one_player_button = self.driver.find_element_by_xpath("//*[@id='main']/div[2]/div[1]")
        one_player_button.click()

        options = self.driver.find_elements_by_xpath("//*[@id='category']/div[2]")
        selected_option = random.randint(0, len(options)-1)
        options[selected_option].click()

        question = self.driver.find_element_by_xpath("//*[@id='main']/div[1]/h3").text

        while question:

            question = self.driver.find_element_by_xpath("//*[@id='main']/div[1]/h3").text
            time.sleep(2)
            question_select = self.driver.find_elements_by_xpath("//*[@id='main']/div[1]/div[1]")
            selected_option = random.randint(0, len(options)-1)
            question_select[selected_option].click()
            time.sleep(2)

            try:
                question = self.driver.find_element_by_xpath(
                    "//div[@id='main']/div[1]/h3"
                    "[@data-bind=\"text: question\"]").text
            except NoSuchElementException:
                print '\tReached end of round.'
                question = None


    def test_wait_2p(self):
        print "Test clicking Two Player button brings user to waiting page."

        self.driver.get("http://0.0.0.0:5000/index.html")
        time.sleep(2)
        two_player_button = self.driver.find_element_by_xpath(
            "//*[@id='main']/div[2]/div[3]")
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
            "//*[@id='main']/div[2]/div[3]")
        two_player_button.click()
        time.sleep(10)

        self.driver.execute_script("window.open()")
        self.driver.switch_to_window(self.driver.window_handles[1])
        self.driver.get("http://0.0.0.0:5000/index.html")
        time.sleep(2)
        two_player_button = self.driver.find_element_by_xpath(
            "//*[@id='main']/div[2]/div[3]")
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
