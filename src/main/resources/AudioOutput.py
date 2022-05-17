global new
get_ipython().system('pip install pip==9.0.3')
import pip
get_ipython().system('pip install gTTS')
from gtts import gTTS

# Choosing language as english
convert = gTTS(text='Sanja Kon', lang='en', slow=False)
# Saving the converted audio in mp3 which can be played
convert.save("audio.mp3")
