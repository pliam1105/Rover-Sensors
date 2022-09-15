using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.NetworkInformation;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class HomePage : Page
    {
        public static Frame select_frame { get; set; }
        public static Frame route_frame { get; set; }
        public static Frame data_frame { get; set; }
        public static Frame info_frame { get; set; }
        public static void LoadPages()
        {
            route_frame.Navigate(typeof(rover_route_small));
            data_frame.Navigate(typeof(sensor_data_small));
            info_frame.Navigate(typeof(planet_info_small));
        }

        public HomePage()
        {
            this.InitializeComponent();
            select_frame = SelectFrame;
            route_frame = RouteFrame;
            data_frame = DataFrame;
            info_frame = InfoFrame;
        }

        public void ShowSelectTip()
        {
            SelectMissionTip.IsOpen = true;
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

        private async void Page_Loaded(object sender, RoutedEventArgs e)
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
                MainPage.GetCurrent().ShowUrlTip();
                return;
            }
            if (GlobalVars.mission_id == 0)
            {
                ShowSelectTip();
            }
            
            select_frame.Navigate(typeof(select_mission_small));
            route_frame.Navigate(typeof(rover_route_small));
            data_frame.Navigate(typeof(sensor_data_small));
            info_frame.Navigate(typeof(planet_info_small));

        }
    }
}
