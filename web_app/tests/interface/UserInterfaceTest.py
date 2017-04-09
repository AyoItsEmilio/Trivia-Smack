import unittest
import time
import random
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException


class UserInterfaceTest(unittest.TestCase):
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
        self.assertEquals('Trivia Smack!', self.driver.title)

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

        count = 0
        while (count < 4):
            question = self.driver.find_element_by_xpath(
                            "//div[@id='main']/div[1]/h3"
                            "[@data-bind=\"text: question\"]").text
            print question
            questionSelect = self.driver.find_elements_by_xpath("//*[@id='main']/div[1]/div[1]")
            selected_option = random.randint(0, len(options)-1)
            questionSelect[selected_option].click()
            count = count + 1
