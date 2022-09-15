using RoverSensors.ContentDialogs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.NetworkInformation;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    public class PlanetItem
    {
        public PlanetItem(string id, string name, string info, bool favorite)
        {
            planet_image = new BitmapImage(new Uri("ms-appx:///Assets/PlanetImages/planet_" + id + ".png"));
            planet_name = name;
            planet_info = info;
            planet_id = id;
            if (favorite)
            {
                favorite_image = new BitmapImage(new Uri("ms-appx:///Assets/star.png"));
            }
            else
            {
                favorite_image = new BitmapImage(new Uri("ms-appx:///Assets/blank.png"));
            }
        }
        public BitmapImage planet_image { get; set; }
        public string planet_name { get; set; }
        public string planet_info { get; set; }
        public BitmapImage favorite_image { get; set; }
        public string planet_id { get; set; }
    }
    public sealed partial class create_mission : Page
    {
        public string StartDateTime;
        public string EndDateTime;
        public string planet_id;

        public bool planet_selected = false;
        public bool start_date_selected = false;
        public bool start_time_selected = false;
        public bool end_date_selected = false;
        public bool end_time_selected = false;

        public List<PlanetItem> planets;
        public create_mission()
        {
            this.InitializeComponent();
        }

        private async void Page_Loaded(object sender, RoutedEventArgs e)
        {
            await LoadPlanets();

        }

        private void DialogClosingEvent(ContentDialog sender, ContentDialogClosingEventArgs args)
        {
            // This mean user does click on Primary or Secondary button
            if (args.Result == ContentDialogResult.None)
            {
                //set selection to sensor data
                MainPage.GetCurrent().SetSelectedNavigationItem(7);
                this.Frame.Navigate(typeof(sensor_data));
                MainPage.last_item_tag = "sensor_data";
            }else if(args.Result == ContentDialogResult.Secondary)
            {
                //set selection to sensor data
                MainPage.GetCurrent().SetSelectedNavigationItem(7);
                this.Frame.Navigate(typeof(sensor_data));
                MainPage.last_item_tag = "sensor_data";
            }
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
        public async Task LoadPlanets()
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
                PositionPopup();
                CreatePopupText.Text = "No Internet";
                CreatePopup.IsOpen = true;
                CreatePopup.Visibility = Visibility.Visible;
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
                PositionPopup();
                CreatePopupText.Text = "Invalid Url";
                CreatePopup.IsOpen = true;
                CreatePopup.Visibility = Visibility.Visible;
                MainPage.GetCurrent().ShowUrlTip();
                return;
            }

            CreatePopupText.Text = "Please Wait...";
            PositionPopup();
            CreatePopup.IsOpen = true;
            CreatePopup.Visibility = Visibility.Visible;

            planets = new List<PlanetItem>();
            PlanetBox.ItemsSource = null;
            PlanetBox.Items.Clear();

            //get data from source
            var client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(new Uri(GetUrl()+"/planets_json.php"));
            var jsonString = await response.Content.ReadAsStringAsync();
            JsonArray root = JsonValue.Parse(jsonString).GetArray();
            if (root.Count > 0)
            {
                for (uint i = 0; i < root.Count; i++)
                {
                    string planet_id = root.GetObjectAt(i).GetNamedString("planet_id");
                    string planet_name = root.GetObjectAt(i).GetNamedString("Name");
                    string distance = root.GetObjectAt(i).GetNamedString("Distance(ly)");
                    string esi = root.GetObjectAt(i).GetNamedString("ESI");

                    string info_text = "Is " + distance + " ly away, with ESI: " + esi;

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

                    planets.Add(new PlanetItem(planet_id, planet_name, info_text, favorite));

                }
            }

            PlanetBox.ItemsSource = planets;

            CreatePopup.IsOpen = false;
            CreatePopup.Visibility = Visibility.Collapsed;

            //request auth password
            PasswordDialog passRequest = new PasswordDialog();
            passRequest.Closing += DialogClosingEvent;
            await passRequest.ShowAsync();
        }

        public void PositionPopup()
        {
            //Stretch popup
            CreatePopupGrid.Width = CreateGrid.ActualWidth;
            CreatePopupGrid.Height = Window.Current.Bounds.Height - 20;

            CreatePopupBorder.Width = CreateGrid.ActualWidth;
            CreatePopupBorder.Height = Window.Current.Bounds.Height - 20;

            CreatePopup.Width = CreateGrid.ActualWidth;
            CreatePopup.Height = Window.Current.Bounds.Height - 20;
        }

        private void CreateGrid_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            PositionPopup();
        }

        private async void SubmitButton_Click(object sender, RoutedEventArgs e)
        {
            string start_date = StartDate.Date.Value.DateTime.ToString("yyyy/MM/dd");
            string start_time = StartTime.Time.ToString();
            StartDateTime = start_date + " " + start_time;

            string end_date = EndDate.Date.Value.DateTime.ToString("yyyy/MM/dd");
            string end_time = EndTime.Time.ToString();
            EndDateTime = end_date + " " + end_time;

            PlanetItem selected_planet = (PlanetItem)PlanetBox.SelectedItem;
            planet_id = selected_planet.planet_id;

            PositionPopup();
            CreatePopup.IsOpen = true;
            CreatePopup.Visibility = Visibility.Visible;

            //get data from source
            var client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(new Uri(GetUrl()+"/mission_manager.php?mission_start=" + StartDateTime + "&mission_end=" + EndDateTime + "&planet_id=" + planet_id));
            var responseString = await response.Content.ReadAsStringAsync();

            CreatePopup.IsOpen = false;
            CreatePopup.Visibility = Visibility.Collapsed;

            if (responseString == "<h1>Mission Created!</h1>")
            {
                //success
                var infoDialog = new ContentDialog
                {
                    Title = "Information",
                    Content = "Mission Created",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "Done"
                };
                await infoDialog.ShowAsync();
            }
            else
            {
                //error
                var infoDialog = new ContentDialog
                {
                    Title = "Information",
                    Content = "Something gone wrong",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "OK"
                };
                await infoDialog.ShowAsync();
            }

        }

        private void PlanetBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            PlanetBox.BorderBrush = new SolidColorBrush(Colors.Green);
            planet_selected = true;
            if (planet_selected && start_date_selected && start_time_selected & end_date_selected && end_time_selected)
            {
                SubmitButton.IsEnabled = true;
            }
        }

        private void StartDate_DateChanged(CalendarDatePicker sender, CalendarDatePickerDateChangedEventArgs args)
        {
            StartDate.BorderBrush = new SolidColorBrush(Colors.Green);
            start_date_selected = true;
            if (planet_selected && start_date_selected && start_time_selected & end_date_selected && end_time_selected)
            {
                SubmitButton.IsEnabled = true;
            }
        }

        private void StartTime_TimeChanged(object sender, TimePickerValueChangedEventArgs e)
        {
            StartTimeBorder.BorderBrush = new SolidColorBrush(Colors.LightGreen);
            start_time_selected = true;
            if (planet_selected && start_date_selected && start_time_selected & end_date_selected && end_time_selected)
            {
                SubmitButton.IsEnabled = true;
            }
        }

        private void EndDate_DateChanged(CalendarDatePicker sender, CalendarDatePickerDateChangedEventArgs args)
        {
            EndDate.BorderBrush = new SolidColorBrush(Colors.Green);
            end_date_selected = true;
            if (planet_selected && start_date_selected && start_time_selected & end_date_selected && end_time_selected)
            {
                SubmitButton.IsEnabled = true;
            }
        }

        private void EndTime_TimeChanged(object sender, TimePickerValueChangedEventArgs e)
        {
            EndTimeBorder.BorderBrush = new SolidColorBrush(Colors.LightGreen);
            end_time_selected = true;
            if (planet_selected && start_date_selected && start_time_selected & end_date_selected && end_time_selected)
            {
                SubmitButton.IsEnabled = true;
            }
        }
    }
}
