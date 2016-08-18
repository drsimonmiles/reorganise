package reorganise.client.components

import japgolly.scalajs.react.ReactNode
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Provides type-safe access to Font Awesome icons
  */
object Icon {
  type Icon = ReactNode

  def apply (name: String): Icon = <.i (^.className := s"fa fa-$name")

  def banned (name: String): Icon =
    <.span (^.className := "fa-stack",
      <.i (^.className := s"fa fa-$name fa-stack-1x"),
      <.i (^.className := "fa fa-ban fa-stack-2x")
    )

  def adjust = apply ("adjust")
  def adn = apply ("adn")
  def alignCenter = apply ("align-center")
  def alignJustify = apply ("align-justify")
  def alignLeft = apply ("align-left")
  def alignRight = apply ("align-right")
  def ambulance = apply ("ambulance")
  def anchor = apply ("anchor")
  def android = apply ("android")
  def angellist = apply ("angellist")
  def angleDoubleDown = apply ("angle-double-down")
  def angleDoubleLeft = apply ("angle-double-left")
  def angleDoubleRight = apply ("angle-double-right")
  def angleDoubleUp = apply ("angle-double-up")
  def angleDown = apply ("angle-down")
  def angleLeft = apply ("angle-left")
  def angleRight = apply ("angle-right")
  def angleUp = apply ("angle-up")
  def apple = apply ("apple")
  def archive = apply ("archive")
  def areaChart = apply ("area-chart")
  def arrowCircleDown = apply ("arrow-circle-down")
  def arrowCircleLeft = apply ("arrow-circle-left")
  def arrowCircleODown = apply ("arrow-circle-o-down")
  def arrowCircleOLeft = apply ("arrow-circle-o-left")
  def arrowCircleORight = apply ("arrow-circle-o-right")
  def arrowCircleOUp = apply ("arrow-circle-o-up")
  def arrowCircleRight = apply ("arrow-circle-right")
  def arrowCircleUp = apply ("arrow-circle-up")
  def arrowDown = apply ("arrow-down")
  def arrowLeft = apply ("arrow-left")
  def arrowRight = apply ("arrow-right")
  def arrowUp = apply ("arrow-up")
  def arrows = apply ("arrows")
  def arrowsAlt = apply ("arrows-alt")
  def arrowsH = apply ("arrows-h")
  def arrowsV = apply ("arrows-v")
  def asterisk = apply ("asterisk")
  def at = apply ("at")
  def automobile = apply ("automobile")
  def backward = apply ("backward")
  def ban = apply ("ban")
  def bank = apply ("bank")
  def barChart = apply ("bar-chart")

  def barChartO = apply ("bar-chart-o")

  def barcode = apply ("barcode")

  def bars = apply ("bars")

  def bed = apply ("bed")

  def beer = apply ("beer")

  def behance = apply ("behance")

  def behanceSquare = apply ("behance-square")

  def bell = apply ("bell")

  def bellO = apply ("bell-o")

  def bellSlash = apply ("bell-slash")

  def bellSlashO = apply ("bell-slash-o")

  def bicycle = apply ("bicycle")

  def binoculars = apply ("binoculars")

  def birthdayCake = apply ("birthday-cake")

  def bitbucket = apply ("bitbucket")

  def bitbucketSquare = apply ("bitbucket-square")

  def bitcoin = apply ("bitcoin")

  def bold = apply ("bold")

  def bolt = apply ("bolt")

  def bomb = apply ("bomb")

  def book = apply ("book")

  def bookmark = apply ("bookmark")

  def bookmarkO = apply ("bookmark-o")

  def briefcase = apply ("briefcase")

  def btc = apply ("btc")

  def bug = apply ("bug")

  def building = apply ("building")

  def buildingO = apply ("building-o")

  def bullhorn = apply ("bullhorn")

  def bullseye = apply ("bullseye")

  def bus = apply ("bus")

  def buysellads = apply ("buysellads")

  def cab = apply ("cab")

  def calculator = apply ("calculator")

  def calendar = apply ("calendar")

  def calendarO = apply ("calendar-o")

  def camera = apply ("camera")

  def cameraRetro = apply ("camera-retro")

  def car = apply ("car")

  def caretDown = apply ("caret-down")

  def caretLeft = apply ("caret-left")

  def caretRight = apply ("caret-right")

  def caretSquareODown = apply ("caret-square-o-down")

  def caretSquareOLeft = apply ("caret-square-o-left")

  def caretSquareORight = apply ("caret-square-o-right")

  def caretSquareOUp = apply ("caret-square-o-up")

  def caretUp = apply ("caret-up")

  def cartArrowDown = apply ("cart-arrow-down")

  def cartPlus = apply ("cart-plus")

  def cc = apply ("cc")

  def ccAmex = apply ("cc-amex")

  def ccDiscover = apply ("cc-discover")

  def ccMastercard = apply ("cc-mastercard")

  def ccPaypal = apply ("cc-paypal")

  def ccStripe = apply ("cc-stripe")

  def ccVisa = apply ("cc-visa")

  def certificate = apply ("certificate")

  def chain = apply ("chain")

  def chainBroken = apply ("chain-broken")

  def check = apply ("check")

  def checkCircle = apply ("check-circle")

  def checkCircleO = apply ("check-circle-o")

  def checkSquare = apply ("check-square")

  def checkSquareO = apply ("check-square-o")

  def chevronCircleDown = apply ("chevron-circle-down")

  def chevronCircleLeft = apply ("chevron-circle-left")

  def chevronCircleRight = apply ("chevron-circle-right")

  def chevronCircleUp = apply ("chevron-circle-up")

  def chevronDown = apply ("chevron-down")

  def chevronLeft = apply ("chevron-left")

  def chevronRight = apply ("chevron-right")

  def chevronUp = apply ("chevron-up")

  def child = apply ("child")

  def circle = apply ("circle")

  def circleO = apply ("circle-o")

  def circleONotch = apply ("circle-o-notch")

  def circleThin = apply ("circle-thin")

  def clipboard = apply ("clipboard")

  def clockO = apply ("clock-o")

  def close = apply ("close")

  def cloud = apply ("cloud")

  def cloudDownload = apply ("cloud-download")

  def cloudUpload = apply ("cloud-upload")

  def cny = apply ("cny")

  def code = apply ("code")

  def codeFork = apply ("code-fork")

  def codepen = apply ("codepen")

  def coffee = apply ("coffee")

  def cog = apply ("cog")

  def cogs = apply ("cogs")

  def columns = apply ("columns")

  def comment = apply ("comment")

  def commentO = apply ("comment-o")

  def comments = apply ("comments")

  def commentsO = apply ("comments-o")

  def compass = apply ("compass")

  def compress = apply ("compress")

  def connectdevelop = apply ("connectdevelop")

  def copy = apply ("copy")

  def copyright = apply ("copyright")

  def creditCard = apply ("credit-card")

  def crop = apply ("crop")

  def crosshairs = apply ("crosshairs")

  def css3 = apply ("css3")

  def cube = apply ("cube")

  def cubes = apply ("cubes")

  def cut = apply ("cut")

  def cutlery = apply ("cutlery")

  def dashboard = apply ("dashboard")

  def dashcube = apply ("dashcube")

  def database = apply ("database")

  def dedent = apply ("dedent")

  def delicious = apply ("delicious")

  def desktop = apply ("desktop")

  def deviantart = apply ("deviantart")

  def diamond = apply ("diamond")

  def digg = apply ("digg")

  def dollar = apply ("dollar")

  def dotCircleO = apply ("dot-circle-o")

  def download = apply ("download")

  def dribbble = apply ("dribbble")

  def dropbox = apply ("dropbox")

  def drupal = apply ("drupal")

  def edit = apply ("edit")

  def eject = apply ("eject")

  def ellipsisH = apply ("ellipsis-h")

  def ellipsisV = apply ("ellipsis-v")

  def empire = apply ("empire")

  def envelope = apply ("envelope")

  def envelopeO = apply ("envelope-o")

  def envelopeSquare = apply ("envelope-square")

  def eraser = apply ("eraser")

  def eur = apply ("eur")

  def euro = apply ("euro")

  def exchange = apply ("exchange")

  def exclamation = apply ("exclamation")

  def exclamationCircle = apply ("exclamation-circle")

  def exclamationTriangle = apply ("exclamation-triangle")

  def expand = apply ("expand")

  def externalLink = apply ("external-link")

  def externalLinkSquare = apply ("external-link-square")

  def eye = apply ("eye")

  def eyeSlash = apply ("eye-slash")

  def eyedropper = apply ("eyedropper")

  def facebook = apply ("facebook")

  def facebookF = apply ("facebook-f")

  def facebookOfficial = apply ("facebook-official")

  def facebookSquare = apply ("facebook-square")

  def fastBackward = apply ("fast-backward")

  def fastForward = apply ("fast-forward")

  def fax = apply ("fax")

  def female = apply ("female")

  def fighterJet = apply ("fighter-jet")

  def file = apply ("file")

  def fileArchiveO = apply ("file-archive-o")

  def fileAudioO = apply ("file-audio-o")

  def fileCodeO = apply ("file-code-o")

  def fileExcelO = apply ("file-excel-o")

  def fileImageO = apply ("file-image-o")

  def fileMovieO = apply ("file-movie-o")

  def fileO = apply ("file-o")

  def filePdfO = apply ("file-pdf-o")

  def filePhotoO = apply ("file-photo-o")

  def filePictureO = apply ("file-picture-o")

  def filePowerpointO = apply ("file-powerpoint-o")

  def fileSoundO = apply ("file-sound-o")

  def fileText = apply ("file-text")

  def fileTextO = apply ("file-text-o")

  def fileVideoO = apply ("file-video-o")

  def fileWordO = apply ("file-word-o")

  def fileZipO = apply ("file-zip-o")

  def filesO = apply ("files-o")

  def film = apply ("film")

  def filter = apply ("filter")

  def fire = apply ("fire")

  def fireExtinguisher = apply ("fire-extinguisher")

  def flag = apply ("flag")

  def flagCheckered = apply ("flag-checkered")

  def flagO = apply ("flag-o")

  def flash = apply ("flash")

  def flask = apply ("flask")

  def flickr = apply ("flickr")

  def floppyO = apply ("floppy-o")

  def folder = apply ("folder")

  def folderO = apply ("folder-o")

  def folderOpen = apply ("folder-open")

  def folderOpenO = apply ("folder-open-o")

  def font = apply ("font")

  def forumbee = apply ("forumbee")

  def forward = apply ("forward")

  def foursquare = apply ("foursquare")

  def frownO = apply ("frown-o")

  def futbolO = apply ("futbol-o")

  def gamepad = apply ("gamepad")

  def gavel = apply ("gavel")

  def gbp = apply ("gbp")

  def ge = apply ("ge")

  def gear = apply ("gear")

  def gears = apply ("gears")

  def genderless = apply ("genderless")

  def gift = apply ("gift")

  def git = apply ("git")

  def gitSquare = apply ("git-square")

  def github = apply ("github")

  def githubAlt = apply ("github-alt")

  def githubSquare = apply ("github-square")

  def gittip = apply ("gittip")

  def glass = apply ("glass")

  def globe = apply ("globe")

  def google = apply ("google")

  def googlePlus = apply ("google-plus")

  def googlePlusSquare = apply ("google-plus-square")

  def googleWallet = apply ("google-wallet")

  def graduationCap = apply ("graduation-cap")

  def gratipay = apply ("gratipay")

  def group = apply ("group")

  def hSquare = apply ("h-square")

  def hackerNews = apply ("hacker-news")

  def handODown = apply ("hand-o-down")

  def handOLeft = apply ("hand-o-left")

  def handORight = apply ("hand-o-right")

  def handOUp = apply ("hand-o-up")

  def hddO = apply ("hdd-o")

  def header = apply ("header")

  def headphones = apply ("headphones")

  def heart = apply ("heart")

  def heartO = apply ("heart-o")

  def heartbeat = apply ("heartbeat")

  def history = apply ("history")

  def home = apply ("home")

  def hospitalO = apply ("hospital-o")

  def hotel = apply ("hotel")

  def html5 = apply ("html5")

  def ils = apply ("ils")

  def image = apply ("image")

  def inbox = apply ("inbox")

  def indent = apply ("indent")

  def info = apply ("info")

  def infoCircle = apply ("info-circle")

  def inr = apply ("inr")

  def instagram = apply ("instagram")

  def institution = apply ("institution")

  def ioxhost = apply ("ioxhost")

  def italic = apply ("italic")

  def joomla = apply ("joomla")

  def jpy = apply ("jpy")

  def jsfiddle = apply ("jsfiddle")

  def key = apply ("key")

  def keyboardO = apply ("keyboard-o")

  def krw = apply ("krw")

  def language = apply ("language")

  def laptop = apply ("laptop")

  def lastfm = apply ("lastfm")

  def lastfmSquare = apply ("lastfm-square")

  def leaf = apply ("leaf")

  def leanpub = apply ("leanpub")

  def legal = apply ("legal")

  def lemonO = apply ("lemon-o")

  def levelDown = apply ("level-down")

  def levelUp = apply ("level-up")

  def lifeBouy = apply ("life-bouy")

  def lifeBuoy = apply ("life-buoy")

  def lifeRing = apply ("life-ring")

  def lifeSaver = apply ("life-saver")

  def lightbulbO = apply ("lightbulb-o")

  def lineChart = apply ("line-chart")

  def link = apply ("link")

  def linkedin = apply ("linkedin")

  def linkedinSquare = apply ("linkedin-square")

  def linux = apply ("linux")

  def list = apply ("list")

  def listAlt = apply ("list-alt")

  def listOl = apply ("list-ol")

  def listUl = apply ("list-ul")

  def locationArrow = apply ("location-arrow")

  def lock = apply ("lock")

  def longArrowDown = apply ("long-arrow-down")

  def longArrowLeft = apply ("long-arrow-left")

  def longArrowRight = apply ("long-arrow-right")

  def longArrowUp = apply ("long-arrow-up")

  def magic = apply ("magic")

  def magnet = apply ("magnet")

  def mailForward = apply ("mail-forward")

  def mailReply = apply ("mail-reply")

  def mailReplyAll = apply ("mail-reply-all")

  def male = apply ("male")

  def mapMarker = apply ("map-marker")

  def mars = apply ("mars")

  def marsDouble = apply ("mars-double")

  def marsStroke = apply ("mars-stroke")

  def marsStrokeH = apply ("mars-stroke-h")

  def marsStrokeV = apply ("mars-stroke-v")

  def maxcdn = apply ("maxcdn")

  def meanpath = apply ("meanpath")

  def medium = apply ("medium")

  def medkit = apply ("medkit")

  def mehO = apply ("meh-o")

  def mercury = apply ("mercury")

  def microphone = apply ("microphone")

  def microphoneSlash = apply ("microphone-slash")

  def minus = apply ("minus")

  def minusCircle = apply ("minus-circle")

  def minusSquare = apply ("minus-square")

  def minusSquareO = apply ("minus-square-o")

  def mobile = apply ("mobile")

  def mobilePhone = apply ("mobile-phone")

  def money = apply ("money")

  def moonO = apply ("moon-o")

  def mortarBoard = apply ("mortar-board")

  def motorcycle = apply ("motorcycle")

  def music = apply ("music")

  def navicon = apply ("navicon")

  def neuter = apply ("neuter")

  def newspaperO = apply ("newspaper-o")

  def openid = apply ("openid")

  def outdent = apply ("outdent")

  def pagelines = apply ("pagelines")

  def paintBrush = apply ("paint-brush")

  def paperPlane = apply ("paper-plane")

  def paperPlaneO = apply ("paper-plane-o")

  def paperclip = apply ("paperclip")

  def paragraph = apply ("paragraph")

  def paste = apply ("paste")

  def pause = apply ("pause")

  def paw = apply ("paw")

  def paypal = apply ("paypal")

  def pencil = apply ("pencil")

  def pencilSquare = apply ("pencil-square")

  def pencilSquareO = apply ("pencil-square-o")

  def phone = apply ("phone")

  def phoneSquare = apply ("phone-square")

  def photo = apply ("photo")

  def pictureO = apply ("picture-o")

  def pieChart = apply ("pie-chart")

  def piedPiper = apply ("pied-piper")

  def piedPiperAlt = apply ("pied-piper-alt")

  def pinterest = apply ("pinterest")

  def pinterestP = apply ("pinterest-p")

  def pinterestSquare = apply ("pinterest-square")

  def plane = apply ("plane")

  def play = apply ("play")

  def playCircle = apply ("play-circle")

  def playCircleO = apply ("play-circle-o")

  def plug = apply ("plug")

  def plus = apply ("plus")

  def plusCircle = apply ("plus-circle")

  def plusSquare = apply ("plus-square")

  def plusSquareO = apply ("plus-square-o")

  def powerOff = apply ("power-off")

  def print = apply ("print")

  def puzzlePiece = apply ("puzzle-piece")

  def qq = apply ("qq")

  def qrcode = apply ("qrcode")

  def question = apply ("question")

  def questionCircle = apply ("question-circle")

  def quoteLeft = apply ("quote-left")

  def quoteRight = apply ("quote-right")

  def ra = apply ("ra")

  def random = apply ("random")

  def rebel = apply ("rebel")

  def recycle = apply ("recycle")

  def reddit = apply ("reddit")

  def redditSquare = apply ("reddit-square")

  def refresh = apply ("refresh")

  def remove = apply ("remove")

  def renren = apply ("renren")

  def reorder = apply ("reorder")

  def repeat = apply ("repeat")

  def reply = apply ("reply")

  def replyAll = apply ("reply-all")

  def retweet = apply ("retweet")

  def rmb = apply ("rmb")

  def road = apply ("road")

  def rocket = apply ("rocket")

  def rotateLeft = apply ("rotate-left")

  def rotateRight = apply ("rotate-right")

  def rouble = apply ("rouble")

  def rss = apply ("rss")

  def rssSquare = apply ("rss-square")

  def rub = apply ("rub")

  def ruble = apply ("ruble")

  def rupee = apply ("rupee")

  def save = apply ("save")

  def scissors = apply ("scissors")

  def search = apply ("search")

  def searchMinus = apply ("search-minus")

  def searchPlus = apply ("search-plus")

  def sellsy = apply ("sellsy")

  def send = apply ("send")

  def sendO = apply ("send-o")

  def server = apply ("server")

  def share = apply ("share")

  def shareAlt = apply ("share-alt")

  def shareAltSquare = apply ("share-alt-square")

  def shareSquare = apply ("share-square")

  def shareSquareO = apply ("share-square-o")

  def shekel = apply ("shekel")

  def sheqel = apply ("sheqel")

  def shield = apply ("shield")

  def ship = apply ("ship")

  def shirtsinbulk = apply ("shirtsinbulk")

  def shoppingCart = apply ("shopping-cart")

  def signIn = apply ("sign-in")

  def signOut = apply ("sign-out")

  def signal = apply ("signal")

  def simplybuilt = apply ("simplybuilt")

  def sitemap = apply ("sitemap")

  def skyatlas = apply ("skyatlas")

  def skype = apply ("skype")

  def slack = apply ("slack")

  def sliders = apply ("sliders")

  def slideshare = apply ("slideshare")

  def smileO = apply ("smile-o")

  def soccerBallO = apply ("soccer-ball-o")

  def sort = apply ("sort")

  def sortAlphaAsc = apply ("sort-alpha-asc")

  def sortAlphaDesc = apply ("sort-alpha-desc")

  def sortAmountAsc = apply ("sort-amount-asc")

  def sortAmountDesc = apply ("sort-amount-desc")

  def sortAsc = apply ("sort-asc")

  def sortDesc = apply ("sort-desc")

  def sortDown = apply ("sort-down")

  def sortNumericAsc = apply ("sort-numeric-asc")

  def sortNumericDesc = apply ("sort-numeric-desc")

  def sortUp = apply ("sort-up")

  def soundcloud = apply ("soundcloud")

  def spaceShuttle = apply ("space-shuttle")

  def spinner = apply ("spinner")

  def spoon = apply ("spoon")

  def spotify = apply ("spotify")

  def square = apply ("square")

  def squareO = apply ("square-o")

  def stackExchange = apply ("stack-exchange")

  def stackOverflow = apply ("stack-overflow")

  def star = apply ("star")

  def starHalf = apply ("star-half")

  def starHalfEmpty = apply ("star-half-empty")

  def starHalfFull = apply ("star-half-full")

  def starHalfO = apply ("star-half-o")

  def starO = apply ("star-o")

  def steam = apply ("steam")

  def steamSquare = apply ("steam-square")

  def stepBackward = apply ("step-backward")

  def stepForward = apply ("step-forward")

  def stethoscope = apply ("stethoscope")

  def stop = apply ("stop")

  def streetView = apply ("street-view")

  def strikethrough = apply ("strikethrough")

  def stumbleupon = apply ("stumbleupon")

  def stumbleuponCircle = apply ("stumbleupon-circle")

  def subscript = apply ("subscript")

  def subway = apply ("subway")

  def suitcase = apply ("suitcase")

  def sunO = apply ("sun-o")

  def superscript = apply ("superscript")

  def support = apply ("support")

  def table = apply ("table")

  def tablet = apply ("tablet")

  def tachometer = apply ("tachometer")

  def tag = apply ("tag")

  def tags = apply ("tags")

  def tasks = apply ("tasks")

  def taxi = apply ("taxi")

  def tencentWeibo = apply ("tencent-weibo")

  def terminal = apply ("terminal")

  def textHeight = apply ("text-height")

  def textWidth = apply ("text-width")

  def th = apply ("th")

  def thLarge = apply ("th-large")

  def thList = apply ("th-list")

  def thumbTack = apply ("thumb-tack")

  def thumbsDown = apply ("thumbs-down")

  def thumbsODown = apply ("thumbs-o-down")

  def thumbsOUp = apply ("thumbs-o-up")

  def thumbsUp = apply ("thumbs-up")

  def ticket = apply ("ticket")

  def times = apply ("times")

  def timesCircle = apply ("times-circle")

  def timesCircleO = apply ("times-circle-o")

  def tint = apply ("tint")

  def toggleDown = apply ("toggle-down")

  def toggleLeft = apply ("toggle-left")

  def toggleOff = apply ("toggle-off")

  def toggleOn = apply ("toggle-on")

  def toggleRight = apply ("toggle-right")

  def toggleUp = apply ("toggle-up")

  def train = apply ("train")

  def transgender = apply ("transgender")

  def transgenderAlt = apply ("transgender-alt")

  def trash = apply ("trash")

  def trashO = apply ("trash-o")

  def tree = apply ("tree")

  def trello = apply ("trello")

  def trophy = apply ("trophy")

  def truck = apply ("truck")

  def `try` = apply ("try")

  def tty = apply ("tty")

  def tumblr = apply ("tumblr")

  def tumblrSquare = apply ("tumblr-square")

  def turkishLira = apply ("turkish-lira")

  def twitch = apply ("twitch")

  def twitter = apply ("twitter")

  def twitterSquare = apply ("twitter-square")

  def umbrella = apply ("umbrella")

  def underline = apply ("underline")

  def undo = apply ("undo")

  def university = apply ("university")

  def unlink = apply ("unlink")

  def unlock = apply ("unlock")

  def unlockAlt = apply ("unlock-alt")

  def unsorted = apply ("unsorted")

  def upload = apply ("upload")

  def usd = apply ("usd")

  def user = apply ("user")

  def userMd = apply ("user-md")

  def userPlus = apply ("user-plus")

  def userSecret = apply ("user-secret")

  def userTimes = apply ("user-times")

  def users = apply ("users")

  def venus = apply ("venus")

  def venusDouble = apply ("venus-double")

  def venusMars = apply ("venus-mars")

  def viacoin = apply ("viacoin")

  def videoCamera = apply ("video-camera")

  def vimeoSquare = apply ("vimeo-square")

  def vine = apply ("vine")

  def vk = apply ("vk")

  def volumeDown = apply ("volume-down")

  def volumeOff = apply ("volume-off")

  def volumeUp = apply ("volume-up")

  def warning = apply ("warning")

  def wechat = apply ("wechat")

  def weibo = apply ("weibo")

  def weixin = apply ("weixin")

  def whatsapp = apply ("whatsapp")

  def wheelchair = apply ("wheelchair")

  def wifi = apply ("wifi")

  def windows = apply ("windows")

  def won = apply ("won")

  def wordpress = apply ("wordpress")

  def wrench = apply ("wrench")

  def xing = apply ("xing")

  def xingSquare = apply ("xing-square")

  def yahoo = apply ("yahoo")

  def yelp = apply ("yelp")

  def yen = apply ("yen")

  def youtube = apply ("youtube")

  def youtubePlay = apply ("youtube-play")

  def youtubeSquare = apply ("youtube-square")
}

