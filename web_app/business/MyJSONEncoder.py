from flask.json import JSONEncoder
from ..objects.Question import Question

class MyJSONEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Question):
            return {
                "question": obj.question,
                "options": obj.options,
                "answer": obj.answer
            }

        return super(MyJSONEncoder, self).default(obj)

