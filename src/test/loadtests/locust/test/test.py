from locust import HttpLocust, TaskSet, task
import random

ids = ["1234", "223454324", "142344543543", "5423452345", " 223554354", "4522345342", " 22354325425", "744567546765",
       "74495567856", " 26334567756"]

def genKey():
    return ''.join(random.choice('0123456789ABCDEF') for i in range(16))

class UserBehavior(TaskSet):


    def on_start(self):
        """ on_start is called when a Locust start before any task is scheduled """

    @task(1)
    def route(l):
        l.client.get("/")

class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait = 1
    max_wait = 1