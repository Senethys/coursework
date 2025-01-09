import cgitb
cgitb.enable()

class Person:
        ''' Person object that holds information about a specific individual.
        Instances are created of "Person" when a user enter values to the "Add
        New Member" form and submits it'''

        #Variables that will hold information about the agent.
        def __init__(self, idnr=0, name='', lastname='', email=''):
                self.idnr = idnr
                self.name = name
                self.lastname = lastname
                self.email = email

        def getId(self):
                return int(self.idnr)

        def getName(self):
                return self.name

        def getLastname(self):
                return self.lastname

        def getEmail(self):
                return self.email
