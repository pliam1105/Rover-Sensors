using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.NetworkInformation;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media.Imaging;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    public class MissionData
    {
        public MissionData(string mission, string planet_id, string name, string duration, bool favorite)
        {
            mission_id = mission;
            planet_image = new BitmapImage(new Uri("ms-appx:///Assets/PlanetImages/planet_" + planet_id + ".png"));
            planet_name = name;
            mission_duration = duration;
            if (favorite)
            {
                favorite_image = new BitmapImage(new Uri("ms-appx:///Assets/star.png"));
            }
            else
            {
                favorite_image = new BitmapImage(new Uri("ms-appx:///Assets/blank.png"));
            }
        }
        public string mission_id { get; set; }
        public BitmapImage planet_image { get; set; }
        public string planet_name { get; set; }
        public string mission_duration { get; set; }
        public BitmapImage favorite_image { get; set; }
    }

    public sealed partial class select_mission : Page
    {

        public List<MissionData> missions;


        public select_mission()
        {
            this.InitializeComponent();
        }

        private async void Page_Loaded(object sender, Windows.UI.Xaml.RoutedEventArgs e)
        {
            await LoadMissions();
        }

        public async Task<string> GetUrlAsync()
        {
            string url;
            //initialize url
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            object _fullurl = localSettings.Values["fullurl"];
            if (_fullurl != null)
            {
                string fullurl = _fullurl as string;
                if (fullurl.Count() > 0)
                {
                    url = fullurl;
                }
                else
                {
                    url = "https://pliamprojects.000webhostapp.com/rover";
                }
            }
            else
            {
                url = "https://pliamprojects.000webhostapp.com/rover";
            }

            //check url
            var client = new HttpClient();
            try
            {
                HttpResponseMessage response = await client.GetAsync(new Uri(url + "/habitable_mission.php?mission_id=1"));
                if (response.IsSuccessStatusCode)
                {
                    //success
                    var responseString = await response.Content.ReadAsStringAsync();
                }
                else
                {
                    //fail
                    url = "";
                }
            }
            catch (HttpRequestException e)
            {
                //fail
                url = "";
            }
            finally
            {
                client.Dispose();
            }

            return url;
        }

        public string GetUrl()
        {
            Task<string> get_url = Task.Run(async () => await GetUrlAsync());
            get_url.Wait();
            string url = get_url.Result;
            return url;
        }
        public async Task LoadMissions()
        {
            bool isInternetConnected = NetworkInterface.GetIsNetworkAvailable();
            if (!isInternetConnected)
            {
                //no internet
                var infoDialog = new ContentDialog
                {
                    Title = "No Internet",
                    Content = "Please check your internet connection and try again",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "OK"
                };
                await infoDialog.ShowAsync();
                SelectPopup.IsOpen = false;
                SelectPopup.Visibility = Visibility.Collapsed;
                return;
            }
            if (GetUrl() == "")
            {
                //fail url
                var infoDialog = new ContentDialog
                {
                    Title = "Invalid Url",
                    Content = "Please insert a valid host url from settings and try again",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "OK"
                };
                await infoDialog.ShowAsync();
                SelectPopup.IsOpen = false;
                SelectPopup.Visibility = Visibility.Collapsed;
                MainPage.GetCurrent().ShowUrlTip();
                return;
            }

            PositionPopup();
            SelectPopup.IsOpen = true;
            SelectPopup.Visibility = Visibility.Visible;

            missions = new List<MissionData>();
            missionsList.ItemsSource = null;
            missionsList.Items.Clear();

            //get data from source
            var client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(new Uri(GetUrl()+"/v_planet_mission_json.php"));
            var jsonString = await response.Content.ReadAsStringAsync();
            JsonArray root = JsonValue.Parse(jsonString).GetArray();
            if (root.Count > 0)
            {
                for (uint i = 0; i < root.Count; i++)
                {
                    string mission_id = root.GetObjectAt(i).GetNamedString("mission_id");
                    string planet_id = root.GetObjectAt(i).GetNamedString("planet_id");
                    string start_time = root.GetObjectAt(i).GetNamedString("start_time");
                    string planet_name = root.GetObjectAt(i).GetNamedString("Name");

                    string duration_text = "Starts at " + start_time;

                    var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;

                    bool favorite;
                    object planet_favorite = localSettings.Values[planet_name];
                    if (planet_favorite != null)
                    {
                        //planet favorite has value
                        bool? favorite_nullable = planet_favorite as bool?;
                        favorite = favorite_nullable.HasValue ? favorite_nullable.Value : false;
                    }
                    else
                    {
                        //planet favorite is null
                        favorite = false;
                    }

                    missions.Add(new MissionData(mission_id, planet_id, planet_name, duration_text, favorite));
                }
            }

            missionsList.ItemsSource = missions;

            SelectPopup.IsOpen = false;
            SelectPopup.Visibility = Visibility.Collapsed;
        }

        public void PositionPopup()
        {
            //Stretch popup
            SelectPopupGrid.Width = SelectGrid.ActualWidth;
            SelectPopupGrid.Height = Window.Current.Bounds.Height - 20;

            SelectPopupBorder.Width = SelectGrid.ActualWidth;
            SelectPopupBorder.Height = Window.Current.Bounds.Height - 20;

            SelectPopup.Width = SelectGrid.ActualWidth;
            SelectPopup.Height = Window.Current.Bounds.Height - 20;
        }

        private void SelectGrid_SizeChanged(object sender, Windows.UI.Xaml.SizeChangedEventArgs e)
        {
            PositionPopup();
        }

        private void MissionsList_ItemClick(object sender, ItemClickEventArgs e)
        {
            MissionData selected_item = (MissionData)e.ClickedItem;
            GlobalVars.mission_id = double.Parse(selected_item.mission_id);
            //set selection to sensor data
            MainPage.GetCurrent().SetSelectedNavigationItem(7);
            this.Frame.Navigate(typeof(sensor_data));
            MainPage.last_item_tag = "sensor_data";
        }

        private async void SelectRefresh_Click(object sender, RoutedEventArgs e)
        {
            await LoadMissions();
        }
    }
}
