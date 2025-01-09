# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()


class WebPage:
        '''
        All pages (classes) inherits from WebPage.
        The given title will be displayed will be displayed on the tab
        '''
        def __init__(self,title):
                self.title=title

        def printHead(self):
                '''
                Prints the html and title tags, including reference to css file.
                '''

                print ("""<link rel="stylesheet" type="text/css" href="css.css">""")
                print ("""<html><head><title>""")
                print (self.title)
                print ("""</title></head><body>""")
                print ("""<a class = "title"
                href="https://www8.cs.umu.se/~kv13smn/5da000/addressbook/">
                The Address Book</a>""")

        def printFooter(self):
                '''
                Print out information in the bottom about the site and creator's
                name, including other information.
                '''
                print("""<p class = "footer"> © Svitri Magnusson, kv13smn, svma0011,
                KV13, Applikationsprogrammering i Python (57100VT15), All rights reserved.
                Umeå, Sweden </p> </body></html>""")

        def printBody(self):
                '''The body contents are handles by the other classes, such that
                WebPage is only resposible for the opening and closing tags of
                a page. Therefore left empty'''
                pass

        def renderPage(self):
                '''
                Calls all classe's methods that prints out the page in the order.
                After these are called, a fully functional webpage is basically
                created.
                '''
                self.printHead()
                self.printBody()
                self.printFooter()

if __name__ == '__main__':
        page=WebPage("New Page")
        page.renderPage()
