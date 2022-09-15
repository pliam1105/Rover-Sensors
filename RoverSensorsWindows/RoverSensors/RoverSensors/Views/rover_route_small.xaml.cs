using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.Devices.Geolocation;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Maps;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class rover_route_small : Page
    {
        public List<BasicGeoposition> routeList;
        public BasicGeoposition position;
        public rover_route_small()
        {
            this.InitializeComponent();
            routeMap.Loaded += RouteMapLoaded;
        }

        private async void RouteMapLoaded(object sender, RoutedEventArgs e)
        {
            routeMap.Style = MapStyle.Aerial;
            routeMap.PanInteractionMode = MapPanInteractionMode.Auto;
            routeMap.RotateInteractionMode = MapInteractionMode.GestureAndControl;
            routeMap.ZoomInteractionMode = MapInteractionMode.GestureAndControl;
            routeMap.TiltInteractionMode = MapInteractionMode.GestureAndControl;

            await LoadRoute();
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
        public async Task LoadRoute()
        {
            PositionPopup();
            RoutePopup.IsOpen = true;
            RoutePopup.Visibility = Visibility.Visible;

            routeMap.MapElements.Clear();

            routeList = new List<BasicGeoposition>();

            //get data from source
            var client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(new Uri(GetUrl()+"/gps_data_json.php?mission_id=" + GlobalVars.mission_id.ToString()));
            var jsonString = await response.Content.ReadAsStringAsync();
            JsonArray root = JsonValue.Parse(jsonString).GetArray();
            if (root.Count > 0)
            {
                for (uint i = 0; i < root.Count; i++)
                {
                    double latitude = double.Parse(root.GetObjectAt(i).GetNamedString("latitude"));
                    double longitude = double.Parse(root.GetObjectAt(i).GetNamedString("longitude"));

                    position = new BasicGeoposition()
                    {
                        Latitude = latitude,
                        Longitude = longitude
                    };

                    routeList.Add(position);
                }

                var pushpin = new MapIcon();
                pushpin.Location = new Geopoint(position);
                pushpin.Title = "ROVER";
                pushpin.CollisionBehaviorDesired = MapElementCollisionBehavior.RemainVisible;
                routeMap.MapElements.Add(pushpin);

                var polyline = new MapPolyline();
                polyline.Path = new Geopath(routeList);
                polyline.StrokeColor = Colors.Blue;
                polyline.StrokeThickness = 5;
                routeMap.MapElements.Add(polyline);

                await routeMap.TrySetViewBoundsAsync(GeoboundingBox.TryCompute(routeList), new Thickness(10), MapAnimationKind.Default);
            }

            RoutePopup.IsOpen = false;
            RoutePopup.Visibility = Visibility.Collapsed;
        }

        public void PositionPopup()
        {
            //Stretch popup
            RoutePopupGrid.Width = RouteGrid.ActualWidth;
            RoutePopupGrid.Height = (Window.Current.Bounds.Height - 20) * 3 / 5;

            RoutePopupBorder.Width = RouteGrid.ActualWidth;
            RoutePopupBorder.Height = (Window.Current.Bounds.Height - 20) * 3 / 5;

            RoutePopup.Width = RouteGrid.ActualWidth;
            RoutePopup.Height = (Window.Current.Bounds.Height - 20) * 3 / 5;
        }

        private void RouteGrid_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            PositionPopup();
        }

        private async void Route_refresh_Click(object sender, RoutedEventArgs e)
        {
            await LoadRoute();
        }
    }
}
